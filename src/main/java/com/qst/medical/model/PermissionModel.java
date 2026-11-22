package com.qst.medical.model;

import com.qst.medical.domain.Permission;

/**
 * 菜单管理业务模型
 */
public class PermissionModel extends Permission {

    private static final long serialVersionUID = 1L;

    /** 存放 meta 封装的各个属性，如 title */
    private MetaModel meta;

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }
}
