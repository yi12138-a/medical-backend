package com.qst.medical.model;

import com.qst.medical.domain.City;

/**
 * 城市信息业务模型，继承 City，用于向前端返回数据
 * 在 City 的基础上新增所在省和城市名
 *
 * @author qst
 */
public class CityModel extends City {

    private static final long serialVersionUID = 1L;

    /** 省份 */
    private String province;

    /** 市 */
    private String city;

    public CityModel() {
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
