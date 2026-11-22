package com.qst.medical.model;

import com.qst.medical.domain.MedicalPolicy;

/**
 * 医保政策返回前端的业务模型
 */
public class MedicalPolicyModel extends MedicalPolicy {

    private static final long serialVersionUID = 1L;

    /** 医保政策包含的所属城市 */
    private CityModel cityModel;

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }
}
