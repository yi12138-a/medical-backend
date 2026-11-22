package com.qst.medical.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 分页查询公共类，供其他实体类继承
 */
public class SuperDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private Long total;

    /** 当前页 */
    @JsonIgnore
    private Integer pn = 1;

    /** 每页大小 */
    @JsonIgnore
    private Integer size = Integer.MAX_VALUE;

    /** 查询关键字 */
    @JsonIgnore
    private String keyword;

    public SuperDomain() {
    }

    public SuperDomain(Long total, Integer pn, Integer size, String keyword) {
        this.total = total;
        this.pn = pn;
        this.size = size;
        this.keyword = keyword;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
