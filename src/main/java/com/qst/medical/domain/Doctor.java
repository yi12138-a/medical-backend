package com.qst.medical.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 医师实体类
 * 对应数据库表：doctor
 */
public class Doctor extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 医师id */
    private Long id;

    /** 医师姓名 */
    @NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "^[\\u2E80-\\u9FFF]{2,4}$", message = "姓名只能为汉字,且长度为2-4个汉字")
    private String name;

    /** 年龄 */
    @Range(min = 1, max = 130, message = "年龄格式错误")
    @NotNull(message = "年龄不能为空")
    private Integer age;

    /** 性别：1男，2女 */
    @Range(min = 1, max = 2, message = "性别错误，1代表男，2代表女")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    /** 等级：1主任，2普通 */
    @NotNull(message = "等级不能为空")
    private Integer levelId;

    /** 电话号 */
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号格式不正确")
    private String phoneNumber;

    /** 诊治类别id */
    @NotNull(message = "级别不能为空")
    private Long typeId;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 账号id */
    private Long accountId;

    public Doctor() {
    }

    public Doctor(Long id, String name, Integer age, Integer sex, Integer levelId, String phoneNumber,
                  Long typeId, Date createTime, Date updateTime, Long accountId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.levelId = levelId;
        this.phoneNumber = phoneNumber;
        this.typeId = typeId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", levelId=" + levelId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", typeId=" + typeId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", accountId=" + accountId +
                '}';
    }
}
