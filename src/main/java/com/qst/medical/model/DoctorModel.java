package com.qst.medical.model;

import com.qst.medical.domain.Doctor;

/**
 * 医师数据模型，用于返回前端数据
 * 在医师实体属性之外，新增诊治类型名称和医师级别名称两个属性
 */
public class DoctorModel extends Doctor {

    private static final long serialVersionUID = 1L;

    /** 诊治类型名称 */
    private String treatType;

    /** 医师级别名称 */
    private String doctorLevel;

    public DoctorModel() {
    }

    public String getTreatType() {
        return treatType;
    }

    public void setTreatType(String treatType) {
        this.treatType = treatType;
    }

    public String getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(String doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    @Override
    public String toString() {
        return "DoctorModel{" +
                "treatType='" + treatType + '\'' +
                ", doctorLevel='" + doctorLevel + '\'' +
                "} " + super.toString();
    }
}
