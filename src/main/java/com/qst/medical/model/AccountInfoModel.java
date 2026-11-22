package com.qst.medical.model;

/**
 * 登录成功后返回给前端的用户信息
 */
public class AccountInfoModel {

    /** 真实姓名 */
    private String realname;

    /** 用户类型（去掉 ROLE_ 前缀） */
    private String utype;

    public AccountInfoModel() {
    }

    public AccountInfoModel(String realname, String utype) {
        this.realname = realname;
        this.utype = utype;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
}
