package com.qst.medical.entity;

import com.qst.medical.domain.CompanyPolicy;
import io.swagger.annotations.ApiModel;

/**
 * 医药公司政策数据模型（实体类）
 * <p>
 * 继承 domain 包的 CompanyPolicy，用于和数据库交互
 * （新增、更新时由 service 层组装后交给 CompanyPolicyMapper 使用）。
 */
@ApiModel(value = "CompanyPolicyEntity", description = "医药公司政策数据库交互实体类")
public class CompanyPolicyEntity extends CompanyPolicy {

    private static final long serialVersionUID = 1L;
}
