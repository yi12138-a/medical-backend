package com.qst.medical.controller;

import com.github.pagehelper.PageInfo;
import com.qst.medical.domain.Sale;
import com.qst.medical.service.SaleService;
import com.qst.medical.util.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * 销售地点（药店）信息控制器
 * <p>
 * 接口路径统一以 /api/sales 开头。
 */
@Api(tags = "药店信息控制器")
@RestController
@RequestMapping("/api/sales")
@CrossOrigin
public class SaleController {

    @Autowired
    private SaleService saleService;

    /**
     * 销售地点信息的分页查询，name 不为空则模糊查询，当路径为 "" 时查询不分页
     *
     * @param pn   页码，可省略
     * @param size 页面大小，可省略
     * @param name 销售地点名关键字，可省略
     * @return 成功返回 data 中 key 为 salePageInfo 的分页信息
     */
    @ApiOperation(value = "销售地点信息的分页查询", notes = "name不为空则按药店名模糊查询，pn、size可省略")
    @GetMapping(value = {"/{pn}/{size}", ""})
    public Msg getSaleWithPage(@PathVariable(value = "pn", required = false) Integer pn,
                               @PathVariable(value = "size", required = false) Integer size,
                               @RequestParam(required = false) String name) {
        PageInfo<Sale> info = saleService.getSaleWithPage(pn, size, name);
        if (info != null) {
            return Msg.success().data("salePageInfo", info);
        }
        return Msg.fail();
    }

    /**
     * 根据id查询一个销售地点
     *
     * @param id 销售地点id
     * @return 成功返回 data 中 key 为 sale 的销售地点信息
     */
    @ApiOperation(value = "根据id查询一个销售地点信息")
    @GetMapping("{id}")
    public Msg getSaleById(@PathVariable("id") Integer id) {
        Msg msg = saleService.getSaleById(id);
        return msg;
    }

    /**
     * 新增一个销售地点信息，仅管理员（角色1）可操作
     *
     * @param sale 销售地点对象
     * @return 成功返回 data 中 key 为 pages 的页码信息
     */
    @ApiOperation(value = "新增一个销售地点信息", notes = "仅管理员可操作，药店名与药店电话不能为空")
    @RolesAllowed({"1"})
    @PostMapping(value = "")
    public Msg saveSale(@RequestBody Sale sale) {
        String name = sale.getSaleName();
        String phone = sale.getSalePhone();
        boolean leaf = (name == null || phone == null || "".equals(name) || "".equals(phone));
        if (leaf) {
            return Msg.fail().mess("填写信息不完整");
        }
        return saleService.saveSale(sale);
    }

    /**
     * 根据id更新销售地点，仅管理员（角色1）可操作
     *
     * @param id   销售地点id
     * @param sale 销售地点对象
     * @return 成功返回“修改成功”
     */
    @ApiOperation(value = "根据id更新销售地点信息", notes = "仅管理员可操作，药店名与药店电话不能为空")
    @RolesAllowed({"1"})
    @PutMapping(value = "/{id}")
    public Msg updateSaleById(@PathVariable("id") Long id, @RequestBody Sale sale) {
        String name = sale.getSaleName();
        String phone = sale.getSalePhone();
        if (name == null || "".equals(name)) {
            return Msg.fail().mess("药店名称不能为空");
        }
        if (phone == null || "".equals(phone)) {
            return Msg.fail().mess("药店电话不能为空");
        }
        Msg msg = saleService.updateSaleById(id, sale);
        return msg;
    }

    /**
     * 根据id删除销售地点，仅管理员（角色1）可操作
     *
     * @param id 销售地点id
     * @return 成功返回“删除成功”
     */
    @ApiOperation(value = "根据id删除销售地点信息", notes = "仅管理员可操作")
    @RolesAllowed({"1"})
    @DeleteMapping("{id}")
    public Msg deleteSaleById(@PathVariable("id") Integer id) {
        Msg msg = saleService.deleteSaleById(id);
        return msg;
    }
}
