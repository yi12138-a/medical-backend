package com.qst.medical.mapper;

import com.qst.medical.domain.Sale;
import com.qst.medical.entity.SaleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 销售地点数据库访问接口
 * <p>
 * 对应映射文件 src/main/resources/mapper/SaleMapper.xml，
 * 接口所在包已被 MedicalApplication 的 @MapperScan 扫描。
 */
@Mapper
@Component
public interface SaleMapper {

    /**
     * 获取所有销售地点信息，name 不为空则按销售地点名模糊查询
     *
     * @param name 销售地点名关键字，可为 null
     * @return 销售地点集合
     */
    List<Sale> getAllSale(@Param("name") String name);

    /**
     * 根据id查找一个销售地点信息
     *
     * @param id 销售地点id
     * @return 销售地点，不存在返回 null
     */
    Sale getSaleById(@Param("id") Integer id);

    /**
     * 添加一个销售地点信息
     *
     * @param sale 销售地点数据模型（total 由 selectKey 回填）
     * @return 受影响行数
     */
    int saveSale(SaleEntity sale);

    /**
     * 根据id删除销售地点信息
     *
     * @param id 销售地点id
     * @return 受影响行数
     */
    int deleteSaleById(@Param("id") Integer id);

    /**
     * 根据id更新销售地点信息
     *
     * @param sale 销售地点对象（saleId 作为条件，updatetime 由 service 设置）
     * @return 受影响行数
     */
    int updateSaleById(Sale sale);
}
