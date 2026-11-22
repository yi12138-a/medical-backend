package com.qst.medical.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.medical.entity.MaterialEntity;
import com.qst.medical.mapper.MaterialMapper;
import com.qst.medical.model.MaterialModel;
import com.qst.medical.param.MaterialParam;
import com.qst.medical.util.Msg;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 必备材料业务层
 * 提供必备材料的分页查询、添加、修改与删除功能
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 分页、关键字查询必备材料信息
     * 如果分页大小为 0（或不传），则设置为 1，即查询单个数据
     *
     * @param param 必备材料查询参数（含 pn、size、keyword）
     * @return 统一响应体，分页信息放入 data 的 materialInfo 中
     */
    public Msg getAllMaterialWithPage(MaterialParam param) {
        // 规格写法为 if (param.getSize() == 0) { param.setSize(1); }
        // 前端传空值（如 ?pn=&size=）时 pn、size 会绑定为 null，先补默认值再判断，避免自动拆箱空指针，行为与规格一致
        if (param.getPn() == null) {
            param.setPn(1);
        }
        if (param.getSize() == null || param.getSize() == 0) {
            param.setSize(1);
        }
        PageHelper.startPage(param.getPn(), param.getSize());
        List<MaterialModel> list = materialMapper.getAllMaterial(param);
        PageInfo<MaterialModel> info = new PageInfo<>(list, 5);
        return Msg.success().data("materialInfo", info);
    }

    /**
     * 查询最新的 4 条医保政策，供主页面数据面板展示使用
     *
     * @return 前 4 条医保政策
     */
    public List<MaterialModel> getFirstMaterialWithPage() {
        PageHelper.startPage(1, 4);
        List<MaterialModel> list = materialMapper.getAllMaterial(new MaterialParam());
        PageInfo<MaterialModel> info = new PageInfo<>(list, 4);
        return info.getList();
    }

    /**
     * 添加必备材料
     * 创建时间与更新时间都置为当前时间，添加成功后根据总记录数计算前端应停留的页码（每页 5 条，向上取整）
     *
     * @param param 必备材料提交参数
     * @return 统一响应体，成功时 data 中含 numberOfAdd（影响行数）与 pages（总页数）
     */
    public Msg saveMaterial(MaterialParam param) {
        param.setCreateTime(new DateTime().toDate());
        param.setUpdateTime(new DateTime().toDate());
        MaterialEntity mEntity = new MaterialEntity();
        // 对象拷贝
        BeanUtils.copyProperties(param, mEntity);
        int i = materialMapper.saveMaterial(mEntity);
        List<MaterialModel> allMaterial = materialMapper.getAllMaterial(null);
        mEntity.setTotal((long) allMaterial.size());
        if (i > 0) {
            Long num = mEntity.getTotal() % 5 == 0 ? (mEntity.getTotal() / 5) : (mEntity.getTotal() / 5) + 1;
            return Msg.success().mess("添加成功").data("numberOfAdd", i).data("pages", num);
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 更新必备材料
     * 更新时间置为当前时间，id 以路径中传入的为准
     *
     * @param id    需要修改的必备材料id
     * @param param 必备材料提交参数
     * @return 统一响应体，成功时返回“修改成功”及修改后的数据，否则返回“修改失败”
     */
    public Msg updateMaterial(Integer id, MaterialParam param) {
        MaterialEntity entity = new MaterialEntity();
        // 对象拷贝
        BeanUtils.copyProperties(param, entity);
        entity.setUpdateTime(new DateTime().toDate());
        entity.setId(id);
        int i = materialMapper.updateMaterial(entity);
        if (i > 0) {
            return Msg.success().mess("修改成功").data("updateData", entity);
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除必备材料
     *
     * @param id 必备材料id
     * @return 统一响应体，成功时返回“删除成功”及删除行数，否则返回“删除失败”
     */
    public Msg deleteMaterial(Long id) {
        int i = materialMapper.deleteMaterial(id);
        if (i > 0) {
            return Msg.success().mess("删除成功").data("numberOfDelete", i);
        }
        return Msg.fail().mess("删除失败");
    }
}
