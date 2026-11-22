package com.qst.medical.mapper;

import com.qst.medical.entity.MaterialEntity;
import com.qst.medical.model.MaterialModel;
import com.qst.medical.param.MaterialParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 必备材料数据访问接口
 * 对应映射文件：src/main/resources/mapper/MaterialMapper.xml
 */
@Mapper
public interface MaterialMapper {

    /*查询所有的材料，param 不为空时按关键字模糊查询 id、title、message*/
    List<MaterialModel> getAllMaterial(MaterialParam param);

    /*新增材料*/
    int saveMaterial(MaterialEntity entity);

    /*更新材料*/
    int updateMaterial(MaterialEntity entity);

    /*删除材料*/
    int deleteMaterial(Long id);
}
