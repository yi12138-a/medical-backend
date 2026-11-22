package com.qst.medical.domain;

/**
 * 诊治类型实体类
 * 对应数据库表：treat_type
 */
public class TreatType extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 诊治类型id */
    private Long id;

    /** 诊治类型名称 */
    private String name;

    public TreatType() {
    }

    public TreatType(Long id, String name) {
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
        return "TreatType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
