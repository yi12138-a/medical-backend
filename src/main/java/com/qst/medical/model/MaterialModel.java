package com.qst.medical.model;

import com.qst.medical.domain.Material;

/**
 * 必备材料业务模型，继承必备材料实体类
 * 用于向前端返回数据
 */
public class MaterialModel extends Material {

    private static final long serialVersionUID = 1L;

    public MaterialModel() {
        super();
    }
}
