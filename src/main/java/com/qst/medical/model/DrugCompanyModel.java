package com.qst.medical.model;

import com.qst.medical.domain.DrugCompany;
import io.swagger.annotations.ApiModel;

/**
 * 医药公司业务模型
 * <p>
 * 用于与前端进行数据交互，继承 domain 包的 DrugCompany，
 * 后续如需对前端隐藏或追加字段，可在此类中扩展。
 */
@ApiModel(value = "DrugCompanyModel", description = "医药公司业务模型")
public class DrugCompanyModel extends DrugCompany {

    private static final long serialVersionUID = 1L;
}
