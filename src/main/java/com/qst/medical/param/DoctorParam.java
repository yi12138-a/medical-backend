package com.qst.medical.param;

import com.qst.medical.domain.Doctor;

/**
 * 医师查询/提交参数模型，用于接收前端传入的医师数据
 * 在医师实体属性之外，新增新增医师时使用的密码属性
 */
public class DoctorParam extends Doctor {

    private static final long serialVersionUID = 1L;

    /** 新增医师的密码 */
    private String pwd;

    public DoctorParam() {
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "DoctorParam{" +
                "pwd='" + pwd + '\'' +
                "} " + super.toString();
    }
}
