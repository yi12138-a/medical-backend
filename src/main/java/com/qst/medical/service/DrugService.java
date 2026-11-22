package com.qst.medical.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.medical.entity.DrugEntity;
import com.qst.medical.mapper.DrugMapper;
import com.qst.medical.model.DrugModel;
import com.qst.medical.model.SaleModel;
import com.qst.medical.param.DrugParam;
import com.qst.medical.util.Msg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 药品信息业务类
 * <p>
 * 负责任务15的药品信息分页查询，以及任务16的药品信息添加、修改、删除；
 * 药品与销售药店之间存在多对多关系，通过 drug_sale 关联表维护。
 */
@Service
@Transactional
public class DrugService {

    @Autowired
    DrugMapper drugMapper;

    /**
     * 分页查询药品信息，name 不为空时按药品名称模糊查询
     *
     * @param pn   当前页码（从 1 开始）
     * @param size 每页条数
     * @param name 药品名称关键字，可为空
     * @return 药品分页信息，每条记录包含该药品的销售药店集合与药店字符串
     */
    public PageInfo<DrugModel> getDrugWithPage(int pn, int size, String name) {
        if (pn <= 0) {
            pn = 1;
        }
        if (size <= 0) {
            size = 5;
        }
        PageHelper.startPage(pn, size);
        List<DrugModel> list = drugMapper.getAllDrug(name);
        // 把 collection 查出的药店集合拼接成前端直接展示的字符串
        if (list != null) {
            for (DrugModel drug : list) {
                drug.setDrugSale(buildDrugSale(drug.getDrugSales()));
            }
        }
        PageInfo<DrugModel> info = new PageInfo<>(list, 5);
        return info;
    }

    /**
     * 新增药品信息，并写入药品与销售药店的关联关系
     *
     * @param drugParam 药品信息（saleIds 为该药品的销售药店id数组）
     * @return 成功时返回前端应展示的总页数 pages
     */
    public Msg saveDrug(DrugParam drugParam) {
        if (drugParam == null) {
            return Msg.fail().mess("药品信息不能为空");
        }
        Msg check = checkRequired(drugParam);
        if (check != null) {
            return check;
        }
        // 药品至少有一个销售地点，saleIds 为空时无法插入 drug_sale
        if (drugParam.getSaleIds() == null || drugParam.getSaleIds().length == 0) {
            return Msg.fail().mess("请选择该药品的销售药店");
        }
        Date now = new Date();
        drugParam.setCreatetime(now);
        drugParam.setUpdatetime(now);
        int i = drugMapper.saveDrug(drugParam);
        int j = drugMapper.insertSalePlace(drugParam.getDrugId(), drugParam.getSaleIds());
        // 由于前端不好判断插入后的总记录数，所以插入之后返回全部的药品数量
        List<DrugModel> allDrug = drugMapper.getAllDrug(null);
        drugParam.setTotal((long) allDrug.size());
        if (i > 0 && j > 0) {
            Long num = drugParam.getTotal() % 5 == 0
                    ? (drugParam.getTotal() / 5)
                    : (drugParam.getTotal() / 5) + 1;
            return Msg.success().data("pages", num).mess("添加成功");
        }
        return Msg.fail().mess("添加失败");
    }

    /**
     * 修改药品信息
     * <p>
     * 药品与药店的关联关系可能整体变动，所以先删除该药品的全部关联，
     * 再根据 saleIds 重新插入；最后更新药品本身的字段。
     *
     * @param id        药品id（来自请求路径）
     * @param drugParam 修改后的药品信息
     * @return 修改结果
     */
    public Msg updateDrug(Long id, DrugParam drugParam) {
        if (drugParam == null) {
            return Msg.fail().mess("药品信息不能为空");
        }
        Msg check = checkRequired(drugParam);
        if (check != null) {
            return check;
        }
        drugParam.setDrugId(id);
        drugParam.setUpdatetime(new Date());
        drugMapper.deleteSaleByDrugId(id);
        if (drugParam.getSaleIds() != null && drugParam.getSaleIds().length > 0) {
            drugMapper.insertSalePlace(id, drugParam.getSaleIds());
        }
        DrugEntity drugEntity = new DrugEntity();
        BeanUtils.copyProperties(drugParam, drugEntity);
        int i = drugMapper.updateDrugById(drugEntity);
        if (i > 0) {
            return Msg.success().mess("修改成功");
        }
        return Msg.fail().mess("修改失败");
    }

    /**
     * 根据id删除药品信息以及药品与药店的关联关系
     * <p>
     * 先删关联表再删药品表，避免 drug_sale 上的外键约束导致药品删除失败。
     *
     * @param drugId 药品id
     * @return 删除结果
     */
    public Msg deleteDrug(Long drugId) {
        if (drugId == null) {
            return Msg.fail().mess("药品id不能为空");
        }
        drugMapper.deleteSaleByDrugId(drugId);
        int i = drugMapper.deleteDrugById(drugId);
        if (i > 0) {
            return Msg.success().mess("删除成功");
        }
        return Msg.fail().mess("删除失败");
    }

    /**
     * 校验药品的必填字段，全部通过时返回 null
     *
     * @param drugParam 待校验的药品信息
     * @return 校验失败时的响应体，成功时为 null
     */
    private Msg checkRequired(DrugParam drugParam) {
        if (!StringUtils.hasText(drugParam.getDrugName())) {
            return Msg.fail().mess("药品名称不能为空");
        }
        if (!StringUtils.hasText(drugParam.getDrugInfo())) {
            return Msg.fail().mess("药品成分信息不能为空");
        }
        if (!StringUtils.hasText(drugParam.getDrugEffect())) {
            return Msg.fail().mess("药品功能作用不能为空");
        }
        if (!StringUtils.hasText(drugParam.getDrugImg())) {
            return Msg.fail().mess("药品图片url不能为空");
        }
        if (!StringUtils.hasText(drugParam.getDrugPublisher())) {
            return Msg.fail().mess("药品发布者不能为空");
        }
        return null;
    }

    /**
     * 把药店集合拼接成逗号分隔的字符串
     *
     * @param sales 该药品的销售药店集合，可为空
     * @return 药店名称字符串，没有药店时返回空串
     */
    private String buildDrugSale(List<SaleModel> sales) {
        if (sales == null || sales.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (SaleModel sale : sales) {
            if (sale == null || !StringUtils.hasText(sale.getSaleName())) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(sale.getSaleName());
        }
        return builder.toString();
    }
}
