package com.qst.medical.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 医药公司实体类
 * <p>
 * 对应数据库表 drugcompany，列名与属性名的对应关系依赖
 * application.yml 中的 map-underscore-to-camel-case: true
 * （company_id -> companyId，company_name -> companyName，company_phone -> companyPhone，
 * updatetime / createtime 列名与属性名一致）
 * <p>
 * 继承 SuperDomain，复用其 total / pn / size / keyword 分页公共属性。
 */
@ApiModel(value = "DrugCompany", description = "医药公司实体类")
public class DrugCompany extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 公司id */
    @ApiModelProperty(value = "公司id")
    private Long companyId;

    /** 公司名 */
    @ApiModelProperty(value = "公司名")
    private String companyName;

    /** 公司电话 */
    @ApiModelProperty(value = "公司电话")
    private String companyPhone;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "DrugCompany{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", updatetime=" + updatetime +
                ", createtime=" + createtime +
                '}';
    }
}
