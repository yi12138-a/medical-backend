package com.qst.medical.mapper;

import com.qst.medical.domain.City;
import com.qst.medical.entity.CityEntity;
import com.qst.medical.model.CityModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 城市信息数据库操作接口
 * （规格中使用 @Component，本项目由 MedicalApplication 上的 @MapperScan 统一注册，故用 @Mapper）
 *
 * @author qst
 */
@Mapper
public interface CityMapper {

    /**
     * 获取所有城市信息，name 不为空则模糊查询（匹配城市名或上级省份名）
     *
     * @param name 查询关键字
     * @return 城市信息列表
     */
    List<CityModel> getAllCity(@Param("name") String name);

    /**
     * 根据 id 查找一个城市信息
     *
     * @param id 城市 id
     * @return 城市信息，不存在返回 null
     */
    City getCityById(@Param("id") Integer id);

    /**
     * 添加一个城市信息
     *
     * @param city 城市信息（total 会被 selectKey 回填）
     * @return 受影响的行数
     */
    int saveCity(CityEntity city);

    /**
     * 根据 id 删除城市信息
     *
     * @param id 城市 id
     * @return 受影响的行数
     */
    int deleteCityById(@Param("id") Integer id);

    /**
     * 查询城市是否存在
     *
     * @param number 城市编号
     * @return 记录数，大于 0 表示已存在
     */
    int checkCityByName(@Param("number") Integer number);
}
