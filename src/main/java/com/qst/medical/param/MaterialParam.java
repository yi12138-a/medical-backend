package com.qst.medical.param;

import com.qst.medical.domain.Material;

/**
 * 必备材料查询参数模型，继承必备材料实体类
 * 用于接收前端提交的查询、添加与修改参数
 */
public class MaterialParam extends Material {

    private static final long serialVersionUID = 1L;

    public MaterialParam() {
        super();
    }
}
