package com.qst.medical.mapper;

import com.qst.medical.domain.DrugCompany;
import com.qst.medical.entity.DrugCompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医药公司数据库操作接口
 * <p>
 * 对应映射文件 src/main/resources/mapper/CompanyMapper.xml。
 * 规格中使用 @Component，本项目由 MedicalApplication 上的
 * {@code @MapperScan("com.qst.medical.mapper")} 统一注册，故用 @Mapper。
 */
@Mapper
public interface CompanyMapper {

    /**
     * 获取所有医药公司信息，name 不为空则模糊查询
     *
     * @param name 公司名关键字，可为 null
     * @return 医药公司集合
     */
    List<DrugCompany> getAllCompany(@Param("name") String name);

    /**
     * 根据id查找一个医药公司信息
     *
     * @param id 公司id
     * @return 医药公司，不存在返回 null
     */
    DrugCompany getCompanyById(@Param("id") Integer id);

    /**
     * 添加一个医药公司信息
     *
     * @param company 医药公司数据模型（total 由 selectKey 回填）
     * @return 受影响行数
     */
    int saveCompany(DrugCompanyEntity company);

    /**
     * 根据id删除医药公司信息
     *
     * @param id 公司id
     * @return 受影响行数
     */
    int deleteCompanyById(@Param("id") Integer id);

    /**
     * 根据id更新医药公司信息
     *
     * @param company 医药公司对象（companyId 作为条件，updatetime 由 service 设置）
     * @return 受影响行数
     */
    int updateCompanyById(DrugCompany company);
}
