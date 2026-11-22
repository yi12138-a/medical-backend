package com.qst.medical.controller;

import com.qst.medical.param.MaterialParam;
import com.qst.medical.service.MaterialService;
import com.qst.medical.util.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * 必备材料控制器
 * 接口路径统一以 /api/materials 开头
 */
@Api(tags = "必备材料控制器")
@RestController
@RequestMapping("/api/materials")
@CrossOrigin
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    /**
     * 分页、关键字查询必备材料
     * 路径：/api/materials?pn=1&size=5&keyword=门诊
     *
     * @param param 必备材料查询参数（含 pn、size、keyword）
     * @return 统一响应体，分页信息放入 data 的 materialInfo 中
     */
    @ApiOperation(value = "必备材料的分页查询", notes = "keyword 不为空时按 id、title、message 模糊查询，pn、size 可省略")
    @GetMapping(value = "")
    public Msg getPolicyWithPage(MaterialParam param) {
        Msg msg = materialService.getAllMaterialWithPage(param);
        return msg;
    }

    /**
     * 添加必备材料，仅管理员（角色1）可操作
     *
     * @param param 必备材料提交参数
     * @return 统一响应体，成功时 data 中含 numberOfAdd 与 pages
     */
    @ApiOperation(value = "添加必备材料", notes = "仅管理员可操作")
    @RolesAllowed({"1"})
    @PostMapping(value = "")
    public Msg saveMedicalPolicy(@RequestBody MaterialParam param) {
        Msg msg = materialService.saveMaterial(param);
        return msg;
    }

    /**
     * 更新必备材料，仅管理员（角色1）可操作
     *
     * @param id    需要修改的必备材料id
     * @param param 必备材料提交参数
     * @return 统一响应体，标题或内容为空时直接返回失败消息
     */
    @ApiOperation(value = "更新必备材料", notes = "仅管理员可操作，标题与内容不能为空")
    @RolesAllowed({"1"})
    @PutMapping(value = "/{id}")
    public Msg updateMedicalPolicy(@PathVariable("id") Integer id, @RequestBody MaterialParam param) {
        if (!StringUtils.hasLength(param.getTitle())) {
            return Msg.fail().mess("标题不能为空");
        }
        if (!StringUtils.hasLength(param.getMessage())) {
            return Msg.fail().mess("内容不能为空");
        }
        return materialService.updateMaterial(id, param);
    }

    /**
     * 根据id删除必备材料，仅管理员（角色1）可操作
     *
     * @param id 必备材料id
     * @return 统一响应体，成功时返回“删除成功”及删除行数
     */
    @ApiOperation(value = "根据id删除必备材料", notes = "仅管理员可操作")
    @RolesAllowed({"1"})
    @DeleteMapping("/{id}")
    public Msg deletePolicy(@PathVariable("id") Long id) {
        return materialService.deleteMaterial(id);
    }
}
