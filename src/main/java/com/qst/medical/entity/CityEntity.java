package com.qst.medical.entity;

import com.qst.medical.domain.City;

/**
 * 城市信息数据交互实体，继承 City，用于与数据库交互
 *
 * @author qst
 */
public class CityEntity extends City {

    private static final long serialVersionUID = 1L;

    /** 城市信息总条数（新增时由 selectKey 回填，用于计算前端跳转页数） */
    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
