package com.qst.medical.param;

import com.qst.medical.domain.Drug;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 药品信息查询/提交参数模型
 * <p>
 * 继承 domain 层的 Drug，用于接收前端传来的药品查询、新增、修改参数；
 * 额外携带售卖该药品的药店id数组（saleIds），供 drug_sale 关联表使用。
 */
@ApiModel(value = "DrugParam", description = "药品信息查询/提交参数")
public class DrugParam extends Drug {

    private static final long serialVersionUID = 1L;

    /** 售卖该药品的药店id数组 */
    @ApiModelProperty(value = "售卖该药品的药店id数组")
    private Long[] saleIds;

    public Long[] getSaleIds() {
        return saleIds;
    }

    public void setSaleIds(Long[] saleIds) {
        this.saleIds = saleIds;
    }
}
