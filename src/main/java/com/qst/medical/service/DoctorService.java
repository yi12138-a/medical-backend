package com.qst.medical.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.medical.entity.AccountEntity;
import com.qst.medical.entity.DoctorEntity;
import com.qst.medical.mapper.DoctorAccountMapper;
import com.qst.medical.mapper.DoctorMapper;
import com.qst.medical.model.DoctorLevelModel;
import com.qst.medical.model.DoctorModel;
import com.qst.medical.model.TreatTypeModel;
import com.qst.medical.param.DoctorParam;
import com.qst.medical.util.Msg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 医师业务类
 * 负责任务3的医师分页查询、医师级别与诊治类型查询，
 * 以及任务4的医师添加、修改、删除和重置密码（同步维护 account 表中的账号）
 */
@Service
@Transactional
public class DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    /** 医师模块的账号表操作（不修改已有的 AccountMapper，见接口注释） */
    @Autowired
    private DoctorAccountMapper doctorAccountMapper;

    /** 密码加密器，使用 WebSecurityConfig 中已定义的 BCryptPasswordEncoder bean */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 分页查询医师信息
     *
     * @param param 查询参数（pn、size、keyword 来自 SuperDomain）
     * @return 分页信息放在 data.doctorInfo 中
     */
    public Msg getDoctorWithPage(DoctorParam param) {
        if (param.getSize() == null || param.getSize() == 0) {
            param.setSize(1);
        }
        if (param.getPn() == null) {
            param.setPn(1);
        }
        PageHelper.startPage(param.getPn(), param.getSize());
        List<DoctorModel> list = doctorMapper.getAllDoctor(param);
        PageInfo<DoctorModel> info = new PageInfo<>(list, 5);
        return Msg.success().data("doctorInfo", info);
    }

    /**
     * 查询所有的医师级别和诊治类型
     *
     * @return data.allTreatType 诊治类型列表，data.allLevel 医师级别列表
     */
    public Msg getLevelAndType() {
        List<TreatTypeModel> allTreatType = doctorMapper.getAllTreatType();
        List<DoctorLevelModel> allLevel = doctorMapper.getAllLevel();
        return Msg.success().data("allTreatType", allTreatType).data("allLevel", allLevel);
    }

    /**
     * 添加医师：先注册账号，再写入医师信息（一个医师对应一个账号）
     *
     * @param param 医师信息，pwd 为新增账号的初始密码
     * @return 添加结果，成功时返回页数 pages 与 addData
     */
    public Msg saveDoctor(DoctorParam param) {
        if (param.getPhoneNumber() == null || param.getPhoneNumber().length() < 11) {
            return Msg.fail().mess("手机号格式不正确");
        }
        if (param.getPwd() == null || param.getPwd().trim().isEmpty()) {
            return Msg.fail().mess("密码不能为空");
        }
        //手机号已被医师使用时不再重复注册账号
        int checkPhone = doctorAccountMapper.checkPhone(param.getPhoneNumber(), null);
        if (checkPhone > 0) {
            return Msg.fail().code(10001).mess("手机号已被使用");
        }
        Date now = new Date();
        AccountEntity aEntity = new AccountEntity();
        aEntity.setPhoneNumber(param.getPhoneNumber());
        //医生用户名：姓名 + 手机号后四位
        aEntity.setUname(param.getName() + param.getPhoneNumber().substring(7));
        aEntity.setRealname(param.getName());
        aEntity.setPwd(passwordEncoder.encode(param.getPwd()));
        aEntity.setCreatetime(now);
        aEntity.setUpdatetime(now);
        //医生身份为 ROLE_2
        aEntity.setUtype("ROLE_2");
        int i;
        try {
            i = doctorAccountMapper.regist(aEntity);
        } catch (DuplicateKeyException e) {
            return Msg.fail().mess("该账号已经注册");
        }
        DoctorEntity de = new DoctorEntity();
        BeanUtils.copyProperties(param, de);
        de.setCreateTime(now);
        de.setUpdateTime(now);
        de.setAccountId(aEntity.getId());
        int j = doctorMapper.saveDoctor(de);
        if (i > 0 && j > 0) {
            List<DoctorModel> allDoctor = doctorMapper.getAllDoctor(null);
            long total = allDoctor.size();
            //前端每页展示5条，按总数计算添加后应展示的页数
            Long num = total % 5 == 0 ? (total / 5) : (total / 5) + 1;
            param.setPwd("");
            return Msg.success().mess("添加成功").data("pages", num).data("addData", param);
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 修改医师信息：同步更新账号中的手机号与用户名
     *
     * @param id    医师id（来自路径变量）
     * @param param 修改后的医师信息
     * @return 修改结果，成功时返回 updateData
     */
    public Msg updateDoctor(Long id, DoctorParam param) {
        if (param.getPhoneNumber() == null || param.getPhoneNumber().length() < 11) {
            return Msg.fail().mess("手机号格式不正确");
        }
        //修改时排除该医师自身，避免手机号未变却提示“已被使用”
        int checkPhone = doctorAccountMapper.checkPhone(param.getPhoneNumber(), id);
        if (checkPhone > 0) {
            return Msg.fail().code(10001).mess("手机号已被使用");
        }
        //前端未传 accountId 时，按医师id补齐，避免更新不到账号
        Long accountId = param.getAccountId();
        if (accountId == null) {
            accountId = doctorAccountMapper.getAccountIdByDoctorId(id);
        }
        int j = 0;
        if (accountId != null) {
            AccountEntity ae = new AccountEntity();
            ae.setId(accountId);
            ae.setUpdatetime(new Date());
            ae.setUname(param.getName() + param.getPhoneNumber().substring(7));
            ae.setPhoneNumber(param.getPhoneNumber());
            j = doctorAccountMapper.updateAccount(ae);
        }
        DoctorEntity de = new DoctorEntity();
        BeanUtils.copyProperties(param, de);
        de.setId(id);
        de.setUpdateTime(new Date());
        int i = doctorMapper.updateDoctor(de);
        if (i > 0 && j > 0) {
            param.setAccountId(accountId);
            param.setPwd("");
            return Msg.success().mess("修改成功").data("updateData", param);
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除医师，同时删除其关联账号
     *
     * @param id 医师id
     * @return 删除结果
     */
    public Msg deleteDoctorById(Long id) {
        int i = doctorMapper.deleteDoctorById(id);
        //多表删除时，医师与账号各删一行，返回受影响行数为2
        if (i >= 2) {
            return Msg.success().mess("删除成功").data("deleteDocInfo", 1).data("deleteDocAccount", 1);
        }
        return Msg.fail().mess("删除失败");
    }

    /**
     * 重置医师账号密码为默认密码 666666
     *
     * @param id 医师id（若该id查不到医师，则按账号id处理，兼容规格中的调用方式）
     * @return 重置结果
     */
    public Msg resetPwd(Long id) {
        //优先按医师id找其关联账号；规格中的接口路径参数为医师id
        Long accountId = doctorAccountMapper.getAccountIdByDoctorId(id);
        if (accountId == null) {
            accountId = id;
        }
        String newPwd = passwordEncoder.encode("666666");
        int i = doctorAccountMapper.resetPwd(accountId, newPwd);
        if (i > 0) {
            return Msg.success().mess("重置成功");
        }
        return Msg.fail().mess("重置失败");
    }
}
