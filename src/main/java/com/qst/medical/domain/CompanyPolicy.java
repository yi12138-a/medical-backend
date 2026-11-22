package com.qst.medical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 医药公司政策实体类
 * <p>
 * 对应数据库表 company_policy，列名与属性名的对应关系依赖
 * application.yml 中的 map-underscore-to-camel-case: true
 * （company_id -> companyId，create_time -> createTime，update_time -> updateTime）。
 */
@ApiModel(value = "CompanyPolicy", description = "医药公司政策实体类")
public class CompanyPolicy extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 医药公司政策id */
    @ApiModelProperty(value = "医药公司政策id")
    private Long id;

    /** 医药公司政策标题 */
    @ApiModelProperty(value = "医药公司政策标题")
    private String title;

    /** 医药公司政策内容 */
    @ApiModelProperty(value = "医药公司政策内容")
    private String message;

    /** 医药公司id */
    @ApiModelProperty(value = "医药公司id")
    private Long companyId;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 发布时间（更新时间） */
    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CompanyPolicy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", companyId=" + companyId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
