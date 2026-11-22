package com.qst.medical.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 销售地点（药店）实体类
 * <p>
 * 对应数据库表 sale，列名与属性名的对应关系依赖
 * application.yml 中的 map-underscore-to-camel-case: true
 * （sale_id -> saleId，sale_name -> saleName，sale_phone -> salePhone，
 * createtime / updatetime 列名与属性名一致）
 */
@ApiModel(value = "Sale", description = "销售地点实体类")
public class Sale extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 销售地点id（药店id） */
    @ApiModelProperty(value = "销售地点id")
    private Long saleId;

    /** 销售地点名（药店名） */
    @ApiModelProperty(value = "销售地点名")
    private String saleName;

    /** 销售地点电话（药店电话） */
    @ApiModelProperty(value = "销售地点电话")
    private String salePhone;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间")
    private Date updatetime;

    /** 经度（用于地图定位） */
    @ApiModelProperty(value = "经度")
    private Double lng;

    /** 纬度（用于地图定位） */
    @ApiModelProperty(value = "纬度")
    private Double lat;

    /** 详细地址 */
    @ApiModelProperty(value = "详细地址")
    private String address;

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSalePhone() {
        return salePhone;
    }

    public void setSalePhone(String salePhone) {
        this.salePhone = salePhone;
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

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", saleName='" + saleName + '\'' +
                ", salePhone='" + salePhone + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", lng=" + lng +
                ", lat=" + lat +
                ", address='" + address + '\'' +
                '}';
    }
}
