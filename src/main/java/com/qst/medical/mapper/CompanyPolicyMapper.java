package com.qst.medical.mapper;

import com.qst.medical.entity.CompanyPolicyEntity;
import com.qst.medical.model.CompanyPolicyModel;
import com.qst.medical.param.CompanyPolicyParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医药公司政策数据库操作接口
 * <p>
 * 对应映射文件 src/main/resources/mapper/CompanyPolicyMapper.xml
 */
@Mapper
public interface CompanyPolicyMapper {

    /**
     * 查询所有的医药公司政策（keyword 不为空时按关键字模糊查询，并关联医药公司信息）
     *
     * @param param 查询参数，可为 null（表示查询全部）
     * @return 医药公司政策业务模型集合
     */
    List<CompanyPolicyModel> getAllPolicy(CompanyPolicyParam param);

    /**
     * 新增医药公司政策
     *
     * @param entity 医药公司政策实体
     * @return 受影响行数
     */
    int savePolicy(CompanyPolicyEntity entity);

    /**
     * 更新医药公司政策
     *
     * @param entity 医药公司政策实体（id 作为更新条件）
     * @return 受影响行数
     */
    int updatePolicy(CompanyPolicyEntity entity);

    /**
     * 根据政策id删除医药公司政策
     *
     * @param id 医药公司政策id
     * @return 受影响行数
     */
    int deletePolicy(@Param("id") Long id);

    /**
     * 根据医药公司id删除该公司下的所有政策
     *
     * @param id 医药公司id
     * @return 受影响行数
     */
    int deletePolicyByCompany(@Param("id") Integer id);
}
