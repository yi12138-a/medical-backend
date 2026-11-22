package com.qst.medical.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 菜单管理
 */
public class Permission extends SuperDomain {

    private static final long serialVersionUID = 1L;

    /** 菜单 id */
    @JsonIgnore
    private Integer id;

    /** 菜单父 id */
    @JsonIgnore
    private Integer pid;

    /** 菜单路径 */
    private String path;

    /** 菜单 name */
    private String name;

    /** 菜单组件 */
    private String component;

    /** 菜单级别 */
    @JsonIgnore
    private Integer level;

    /** 子菜单 */
    private List<Permission> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }
}
