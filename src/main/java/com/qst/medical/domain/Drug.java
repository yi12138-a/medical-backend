package com.qst.medical.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 药品实体类
 * <p>
 * 对应数据库表 drug。列名与属性名的对应关系依赖
 * application.yml 中的 map-underscore-to-camel-case: true
 * （drug_id -&gt; drugId，drug_name -&gt; drugName，drug_info -&gt; drugInfo，
 * drug_effect -&gt; drugEffect，drug_img -&gt; drugImg，publisher -&gt; drugPublisher，
 * createtime / updatetime 列名与属性名一致）。
 * <p>
 * 继承 SuperDomain，复用其 total / pn / size / keyword 分页公共属性。
 */
@ApiModel(value = "Drug", description = "药品实体类")
public class Drug extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 药品id */
    @ApiModelProperty(value = "药品id")
    private Long drugId;

    /** 药品名称 */
    @ApiModelProperty(value = "药品名称")
    @NotNull(message = "药品名称不能为空")
    private String drugName;

    /** 药品成分信息 */
    @ApiModelProperty(value = "药品成分信息")
    @NotNull(message = "药品成分信息不能为空")
    private String drugInfo;

    /** 药品功能作用 */
    @ApiModelProperty(value = "药品功能作用")
    @NotNull(message = "药品功能作用不能为空")
    private String drugEffect;

    /** 药品图片url */
    @ApiModelProperty(value = "药品图片url")
    @NotNull(message = "药品图片url不能为空")
    private String drugImg;

    /** 药品创建时间 */
    @ApiModelProperty(value = "药品创建时间")
    private Date createtime;

    /** 药品更新时间 */
    @ApiModelProperty(value = "药品更新时间")
    private Date updatetime;

    /** 药品发布者 */
    @ApiModelProperty(value = "药品发布者")
    @NotNull(message = "药品发布者不能为空")
    private String drugPublisher;

    public Drug() {
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(String drugInfo) {
        this.drugInfo = drugInfo;
    }

    public String getDrugEffect() {
        return drugEffect;
    }

    public void setDrugEffect(String drugEffect) {
        this.drugEffect = drugEffect;
    }

    public String getDrugImg() {
        return drugImg;
    }

    public void setDrugImg(String drugImg) {
        this.drugImg = drugImg;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getDrugPublisher() {
        return drugPublisher;
    }

    public void setDrugPublisher(String drugPublisher) {
        this.drugPublisher = drugPublisher;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "drugId=" + drugId +
                ", drugName='" + drugName + '\'' +
                ", drugInfo='" + drugInfo + '\'' +
                ", drugEffect='" + drugEffect + '\'' +
                ", drugImg='" + drugImg + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", drugPublisher='" + drugPublisher + '\'' +
                '}';
    }
}
