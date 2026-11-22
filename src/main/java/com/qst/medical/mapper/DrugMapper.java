package com.qst.medical.mapper;

import com.qst.medical.entity.DrugEntity;
import com.qst.medical.model.DrugModel;
import com.qst.medical.model.SaleModel;
import com.qst.medical.param.DrugParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品信息数据访问接口
 * <p>
 * 对应映射文件：src/main/resources/mapper/DrugMapper.xml
 * （namespace 为本接口全限定名，语句 id 与下面的方法名一致）。
 */
@Mapper
public interface DrugMapper {

    /*查询所有药品信息，name 不为空时按药品名称模糊查询，同时封装药品的销售药店*/
    List<DrugModel> getAllDrug(@Param("name") String name);

    /*查询指定药品id对应的所有销售药店（供 resultMap 的 collection 嵌套查询调用）*/
    List<SaleModel> getWithSale(@Param("drugId") Long drugId);

    /*新增药品信息，插入成功后把自增主键回填到 param.drugId*/
    int saveDrug(DrugParam drugParam);

    /*根据id更新药品信息*/
    int updateDrugById(DrugEntity drug);

    /*插入药品与其销售药店的关联关系（drug_sale）*/
    int insertSalePlace(@Param("drugId") Long drugId, @Param("saleIds") Long[] saleIds);

    /*根据药品id删除对应的销售药店关联关系*/
    int deleteSaleByDrugId(@Param("drugId") Long drugId);

    /*根据药品id删除药品信息*/
    int deleteDrugById(@Param("drugId") Long drugId);
}
