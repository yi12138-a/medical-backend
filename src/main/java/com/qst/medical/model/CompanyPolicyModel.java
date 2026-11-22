package com.qst.medical.model;

import com.qst.medical.domain.CompanyPolicy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 医药公司政策业务模型
 * <p>
 * 用于与前端进行数据交互，在医药公司政策属性之外，
 * 额外携带该政策所属的医药公司信息（drugCompanyModel）。
 */
@ApiModel(value = "CompanyPolicyModel", description = "医药公司政策业务模型")
public class CompanyPolicyModel extends CompanyPolicy {

    private static final long serialVersionUID = 1L;

    /** 该政策所属的医药公司信息 */
    @ApiModelProperty(value = "所属医药公司信息")
    private DrugCompanyModel drugCompanyModel;

    public DrugCompanyModel getDrugCompanyModel() {
        return drugCompanyModel;
    }

    public void setDrugCompanyModel(DrugCompanyModel drugCompanyModel) {
        this.drugCompanyModel = drugCompanyModel;
    }
}
