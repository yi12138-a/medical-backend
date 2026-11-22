package com.qst.medical.domain;

import java.util.Date;

/**
 * 必备材料实体类
 * 对应数据库表：material
 */
public class Material extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 材料id */
    private Integer id;

    /** 材料标题 */
    private String title;

    /** 材料内容 */
    private String message;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public Material() {
    }

    public Material(Integer id, String title, String message, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return "Material{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
