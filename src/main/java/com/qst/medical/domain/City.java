package com.qst.medical.domain;

import java.util.Date;

/**
 * 城市信息实体类
 * 继承 SuperDomain，复用 total、pn、size、keyword 等分页公共属性
 *
 * @author qst
 */
public class City extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 城市id */
    private Long cityId;

    /** 城市编号（对应 sysregion 表的 id） */
    private Integer cityNumber;

    /** 创建时间 */
    private Date createtime;

    /** 修改时间 */
    private Date updatetime;

    public City() {
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Integer getCityNumber() {
        return cityNumber;
    }

    public void setCityNumber(Integer cityNumber) {
        this.cityNumber = cityNumber;
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
}
