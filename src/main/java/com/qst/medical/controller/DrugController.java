package com.qst.medical.controller;

import com.github.pagehelper.PageInfo;
import com.qst.medical.model.DrugModel;
import com.qst.medical.param.DrugParam;
import com.qst.medical.service.DrugService;
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
import java.util.Date;

/**
 * 药品控制器类
 * <p>
 * 提供药品信息的分页查询（任务15）以及添加、修改、删除（任务16）接口，
 * 图片上传接口见 FileUploadController（/api/base/upload）。
 */
@Api(tags = "药品控制器类")
@RestController
@RequestMapping("/api/drugs")
@CrossOrigin
public class DrugController {

    @Autowired
    DrugService drugService;

    /**
     * 药品信息的分页查询，name 不为空则按药品名称模糊查询
     *
     * @param pn   页码
     * @param size 每页条数
     * @param name 药品名称关键字
     * @return 统一响应体，data 中 drugPageInfo 为分页结果
     */
    @ApiOperation(value = "分页查询药品信息")
    @GetMapping("/{pn}/{size}")
    public Msg getDrugWithPage(@PathVariable("pn") int pn,
                               @PathVariable("size") int size,
                               @RequestParam(value = "name", required = false) String name) {
        PageInfo<DrugModel> info = drugService.getDrugWithPage(pn, size, name);
        if (info != null) {
            return Msg.success().data("drugPageInfo", info);
        }
        return Msg.fail();
    }

    /**
     * 新增药品信息
     *
     * @param drugParam 药品信息（含销售药店数组 saleIds）
     * @return 添加结果
     */
    @ApiOperation(value = "新增药品信息")
    @RolesAllowed({"1", "2"})
    @PostMapping(value = "")
    public Msg saveDrug(@RequestBody DrugParam drugParam) {
        drugParam.setCreatetime(new Date());
        drugParam.setUpdatetime(new Date());
        return drugService.saveDrug(drugParam);
    }

    /**
     * 修改药品信息
     *
     * @param id        药品id
     * @param drugParam 修改后的药品信息
     * @return 修改结果
     */
    @ApiOperation(value = "修改药品信息")
    @RolesAllowed({"1"})
    @PutMapping(value = "/{id}")
    public Msg updateDrug(@PathVariable("id") Long id, @RequestBody DrugParam drugParam) {
        return drugService.updateDrug(id, drugParam);
    }

    /**
     * 根据id删除药品信息以及药品-药店关联表的信息
     *
     * @param drugId 药品id
     * @return 删除结果
     */
    @ApiOperation(value = "删除药品信息")
    @RolesAllowed({"1"})
    @DeleteMapping(value = "/{drugId}")
    public Msg deleteDrug(@PathVariable("drugId") Long drugId) {
        return drugService.deleteDrug(drugId);
    }
}
