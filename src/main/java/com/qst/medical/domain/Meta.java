package com.qst.medical.domain;

/**
 * 菜单标题
 */
public class Meta extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 菜单 title */
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
