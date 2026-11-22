package com.qst.medical.domain;

import javax.validation.constraints.Pattern;

/**
 * 医保政策实体类
 */
public class MedicalPolicy extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 政策id */
    @Pattern(regexp = "(^[0-9]*)", message = "编号只能为数字")
    private Long id;

    /** 政策标题 */
    private String title;

    /** 政策消息（政策内容） */
    private String message;

    /** 政策的对应城市id */
    private Long cityId;

    /** 创建时间 */
    private String createTime;

    /** 更新时间兼发布时间 */
    private String updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MedicalPolicy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", cityId=" + cityId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
