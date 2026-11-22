package com.qst.medical.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.medical.domain.Sale;
import com.qst.medical.entity.SaleEntity;
import com.qst.medical.mapper.SaleMapper;
import com.qst.medical.util.Msg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 销售地点服务层
 * <p>
 * 提供销售地点分页查询、根据id查询、添加、修改、删除功能。
 */
@Service
@Transactional
public class SaleService {

    @Autowired
    private SaleMapper saleMapper;

    /**
     * 获取所有销售地点信息并分页，name 不为空则模糊查询
     * <p>
     * 规格中 pn、size 同为 null 时置为 1 和 0 表示“不进行分页”；
     * 由于 PageHelper 对每页大小 0 会生成 limit 0（返回空集），
     * 这里按规格注释表达的意图用 Integer.MAX_VALUE 表示“不限制每页条数”。
     *
     * @param pn   页码，为空时默认为 1
     * @param size 页面大小，为空或非正数时不分页
     * @param name 销售地点名关键字，可为 null
     * @return 分页信息
     */
    public PageInfo<Sale> getSaleWithPage(Integer pn, Integer size, String name) {
        if (pn == null && size == null) {
            pn = 1;
            size = Integer.MAX_VALUE;
        }
        if (pn == null || pn < 1) {
            pn = 1;
        }
        if (size == null || size < 1) {
            size = Integer.MAX_VALUE;
        }
        PageHelper.startPage(pn, size);
        List<Sale> list = saleMapper.getAllSale(name);
        PageInfo<Sale> info = new PageInfo<>(list, 5);
        return info;
    }

    /**
     * 根据id查找一个销售地点
     *
     * @param id 销售地点id
     * @return 成功返回 data 中 key 为 sale 的销售地点信息，找不到返回“没有找到”
     */
    public Msg getSaleById(Integer id) {
        Sale sale = saleMapper.getSaleById(id);

        if (sale == null) {
            return Msg.fail().mess("没有找到");
        }
        return Msg.success().data("sale", sale);
    }

    /**
     * 添加一个销售地点
     * <p>
     * 创建时间与更新时间都置为当前时间，添加成功后根据总记录数计算前端应停留的页码
     * （每页 5 条，向上取整）。
     *
     * @param sale 销售地点对象
     * @return 成功返回 data 中 key 为 pages 的页码信息
     */
    public Msg saveSale(Sale sale) {
        Date d = new Date();
        sale.setCreatetime(d);
        sale.setUpdatetime(d);
        SaleEntity se = new SaleEntity();
        BeanUtils.copyProperties(sale, se);//对象拷贝
        try {
            int i = saleMapper.saveSale(se);
            if (i > 0) {
                Long total = se.getTotal() == null ? 0L : se.getTotal();
                Long num = total % 5 == 0 ? (total / 5) : (total / 5) + 1;
                return Msg.success().data("pages", num).mess("添加成功");
            }
            return Msg.fail().mess("添加失败");
        } catch (Exception e) {
            return Msg.fail().mess("添加失败");
        }
    }

    /**
     * 根据id更新销售地点信息
     *
     * @param id   销售地点id
     * @param sale 销售地点对象
     * @return 成功返回“修改成功”，否则返回“修改失败”
     */
    public Msg updateSaleById(Long id, Sale sale) {
        sale.setUpdatetime(new Date());
        sale.setSaleId(id);
        int i = saleMapper.updateSaleById(sale);
        if (i > 0) {
            return Msg.success().mess("修改成功");
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除销售地点信息
     *
     * @param id 销售地点id
     * @return 成功返回“删除成功”，否则返回“删除失败”
     */
    public Msg deleteSaleById(Integer id) {
        int i = saleMapper.deleteSaleById(id);
        if (i > 0) {
            return Msg.success().mess("删除成功");
        } else {
            return Msg.fail().mess("删除失败");
        }
    }
}
