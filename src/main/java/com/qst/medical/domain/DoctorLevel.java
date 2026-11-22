package com.qst.medical.domain;

/**
 * 医师级别实体类
 * 对应数据库表：doctor_level
 */
public class DoctorLevel extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 级别id */
    private Long id;

    /** 级别名称 */
    private String name;

    public DoctorLevel() {
    }

    public DoctorLevel(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "DoctorLevel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
