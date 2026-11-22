package com.qst.medical.model;

import com.qst.medical.domain.Sale;
import io.swagger.annotations.ApiModel;

/**
 * 销售地点业务模型
 * <p>
 * 继承 domain 层的 Sale，用于与前端进行数据交互（后续任务按需扩展字段）。
 */
@ApiModel(value = "SaleModel", description = "销售地点业务模型")
public class SaleModel extends Sale {

    private static final long serialVersionUID = 1L;
}
