package com.qst.medical.param;

import com.qst.medical.domain.CompanyPolicy;
import io.swagger.annotations.ApiModel;

/**
 * 医药公司政策查询/提交参数
 * <p>
 * 用于接收前端查询数据（关键字、分页参数由父类 SuperDomain 提供）
 * 以及新增、修改时提交的医药公司政策数据。
 */
@ApiModel(value = "CompanyPolicyParam", description = "医药公司政策查询/提交参数")
public class CompanyPolicyParam extends CompanyPolicy {

    private static final long serialVersionUID = 1L;
}
