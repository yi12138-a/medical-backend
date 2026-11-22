package com.qst.medical.entity;

import com.qst.medical.domain.Drug;

/**
 * 药品信息数据库交互实体
 * <p>
 * 继承 domain 层的 Drug，用于与数据库进行数据交互（新增、修改时作为参数对象）。
 */
public class DrugEntity extends Drug {

    private static final long serialVersionUID = 1L;
}
