package com.qst.medical.entity;

import com.qst.medical.domain.Sale;

/**
 * 销售地点数据库交互实体
 * <p>
 * 继承 domain 层的 Sale，额外保存插入成功后返回的总记录数 total，
 * total 由 SaleMapper.xml 中 saveSale 的 selectKey 回填。
 */
public class SaleEntity extends Sale {

    private static final long serialVersionUID = 1L;

    /** 插入成功后返回的总记录数 */
    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
