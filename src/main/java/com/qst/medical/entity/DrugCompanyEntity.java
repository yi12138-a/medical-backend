package com.qst.medical.entity;

import com.qst.medical.domain.DrugCompany;
import io.swagger.annotations.ApiModel;

/**
 * 医药公司数据模型（实体类）
 * <p>
 * 继承 domain 包的 DrugCompany，用于和数据库交互。
 * 规格中的 total 属性（插入后存储总记录数）由父类
 * {@link com.qst.medical.domain.SuperDomain} 提供，此处不再重复声明，
 * 避免与父类同名字段相互遮蔽。
 * CompanyMapper.xml 中 saveCompany 的
 * &lt;selectKey order="AFTER" keyProperty="total"&gt; 会通过父类的 setTotal 回填该值，
 * service 层据此计算添加数据后前端应停留的页码。
 */
@ApiModel(value = "DrugCompanyEntity", description = "医药公司数据库交互实体类")
public class DrugCompanyEntity extends DrugCompany {

    private static final long serialVersionUID = 1L;
}
