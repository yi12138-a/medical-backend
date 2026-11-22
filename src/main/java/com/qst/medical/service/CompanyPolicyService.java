package com.qst.medical.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.medical.entity.CompanyPolicyEntity;
import com.qst.medical.mapper.CompanyPolicyMapper;
import com.qst.medical.model.CompanyPolicyModel;
import com.qst.medical.param.CompanyPolicyParam;
import com.qst.medical.util.Msg;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医药公司政策业务逻辑层
 * <p>
 * 提供医药公司政策的分页关键字查询，以及新增、修改、删除功能。
 */
@Service
@Transactional
public class CompanyPolicyService {

    @Autowired
    private CompanyPolicyMapper companyPolicyMapper;

    /**
     * 分页、关键字查询医药公司政策信息
     *
     * @param param 医药公司政策查询参数（pn 当前页、size 每页大小、keyword 关键字）
     * @return 统一响应体，data 中 key 为 policyInfo 的是分页结果
     */
    public Msg getAllPolicyWithPage(CompanyPolicyParam param) {
        // 页面大小为空时按 SuperDomain 的默认值（不分页）处理，为 0 时按规格设为 1
        if (param.getPn() == null || param.getPn() < 1) {
            param.setPn(1);
        }
        if (param.getSize() == null) {
            param.setSize(Integer.MAX_VALUE);
        }
        if (param.getSize() == 0) {
            param.setSize(1);
        }
        PageHelper.startPage(param.getPn(), param.getSize());
        List<CompanyPolicyModel> list = companyPolicyMapper.getAllPolicy(param);
        PageInfo<CompanyPolicyModel> info = new PageInfo<>(list, 5);
        return Msg.success().data("policyInfo", info);
    }

    /**
     * 查询最新的 4 条医药公司政策，供主页面数据面板展示使用
     *
     * @return 前 4 条医药公司政策
     */
    public List<CompanyPolicyModel> getFirstPolicyWithPage() {
        PageHelper.startPage(1, 4);
        List<CompanyPolicyModel> list = companyPolicyMapper.getAllPolicy(new CompanyPolicyParam());
        PageInfo<CompanyPolicyModel> info = new PageInfo<>(list, 4);
        return info.getList();
    }

    /**
     * 添加医药公司政策
     * <p>
     * 创建时间与发布时间都置为当前时间，添加成功后统计总记录数，
     * 并按每页 5 条计算前端应停留的页码（向上取整）。
     *
     * @param param 医药公司政策参数
     * @return 添加结果，data 中 numberOfAdd 为受影响行数、pages 为总页数
     */
    public Msg savePolicy(CompanyPolicyParam param) {
        param.setCreateTime(new DateTime().toDate());
        param.setUpdateTime(new DateTime().toDate());
        CompanyPolicyEntity mpEntity = new CompanyPolicyEntity();
        BeanUtils.copyProperties(param, mpEntity);
        int i = companyPolicyMapper.savePolicy(mpEntity);
        // 统计全部政策的条数，用于计算添加数据后前端应停留的页码
        List<CompanyPolicyModel> allPolicy = companyPolicyMapper.getAllPolicy(null);
        mpEntity.setTotal((long) allPolicy.size());
        if (i > 0) {
            Long num = mpEntity.getTotal() % 5 == 0 ? (mpEntity.getTotal() / 5) : (mpEntity.getTotal() / 5) + 1;
            return Msg.success().mess("添加成功").data("numberOfAdd", i).data("pages", num);
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 更新医药公司政策
     * <p>
     * 发布时间由后端置为当前时间，政策id以路径参数为准。
     *
     * @param id    医药公司政策id
     * @param param 医药公司政策参数
     * @return 修改结果，data 中 updateData 为更新后的医药公司政策实体
     */
    public Msg updatePolicy(Long id, CompanyPolicyParam param) {
        CompanyPolicyEntity entity = new CompanyPolicyEntity();
        BeanUtils.copyProperties(param, entity);
        entity.setUpdateTime(new DateTime().toDate());
        entity.setId(id);
        int i = companyPolicyMapper.updatePolicy(entity);
        if (i > 0) {
            return Msg.success().mess("修改成功").data("updateData", entity);
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除医药公司政策
     *
     * @param id 医药公司政策id
     * @return 删除结果，data 中 numberOfDelete 为受影响行数
     */
    public Msg deletePolicy(Long id) {
        int i = companyPolicyMapper.deletePolicy(id);
        if (i > 0) {
            return Msg.success().mess("删除成功").data("numberOfDelete", i);
        }
        return Msg.fail().mess("删除失败");
    }
}
