package com.qst.medical.model;

import com.qst.medical.domain.Drug;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 药品信息业务模型
 * <p>
 * 继承 domain 层的 Drug，用于与前端进行数据交互；
 * 额外提供该药品的销售药店集合（drugSales，由 DrugMapper.xml 的 collection 嵌套查询填充）
 * 以及供前端直接展示的销售药店字符串（drugSale，由 Service 依据 drugSales 拼接）。
 */
@ApiModel(value = "DrugModel", description = "药品信息业务模型")
public class DrugModel extends Drug {

    private static final long serialVersionUID = 1L;

    /** 销售该药的药店（集合类型，有多个） */
    @ApiModelProperty(value = "销售该药的药店集合")
    private List<SaleModel> drugSales;

    /** 销售该药的药店地址（字符串类型，有多个，使用逗号分隔） */
    @ApiModelProperty(value = "销售该药的药店字符串（逗号分隔）")
    private String drugSale;

    public List<SaleModel> getDrugSales() {
        return drugSales;
    }

    public void setDrugSales(List<SaleModel> drugSales) {
        this.drugSales = drugSales;
    }

    public String getDrugSale() {
        return drugSale;
    }

    public void setDrugSale(String drugSale) {
        this.drugSale = drugSale;
    }

    @Override
    public String toString() {
        return "DrugModel{" +
                "drugSales=" + drugSales +
                ", drugSale='" + drugSale + '\'' +
                '}';
    }
}
