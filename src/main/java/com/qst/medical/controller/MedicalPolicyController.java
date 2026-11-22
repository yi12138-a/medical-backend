package com.qst.medical.controller;

import com.qst.medical.param.MedicalPolicyParam;
import com.qst.medical.service.MedicalPolicyService;
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
 * 医保政策控制器类
 */
@Api(tags = "医保政策控制器类")
@RestController
@RequestMapping("/api/medical_policys")
@CrossOrigin
public class MedicalPolicyController {

    @Autowired
    MedicalPolicyService medicalPolicyService;

    /**
     * 条件查询医保政策信息（分页）
     * @param param 医保政策查询参数
     * @return 医保政策分页数据
     */
    @ApiOperation(value = "分页条件查询医保政策", notes = "支持按编号、标题、更新时间、城市id条件查询，pn、size可省略")
    @GetMapping(value = "")
    public Msg getMedicalPolicyWithPage(MedicalPolicyParam param) {
        Msg msg = medicalPolicyService.getMedicalPolicyWithPage(param);
        return msg;
    }

    /**
     * 添加医保信息
     * @param param 医保政策参数
     * @return 添加结果
     */
    @ApiOperation(value = "添加医保政策", notes = "仅管理员可操作，创建时间由后端生成")
    @RolesAllowed({"1"})
    @PostMapping(value = "")
    public Msg saveMedicalPolicy(@RequestBody MedicalPolicyParam param) {
        Msg msg = medicalPolicyService.saveMedicalPolicy(param);
        return msg;
    }

    /**
     * 更新医保信息
     * @param id 医保政策id
     * @param param 医保政策参数
     * @return 修改结果
     */
    @ApiOperation(value = "更新医保政策", notes = "仅管理员可操作，标题、内容、城市不能为空")
    @RolesAllowed({"1"})
    @PutMapping(value = "/{id}")
    public Msg updateMedicalPolicy(@PathVariable("id") Long id, @RequestBody MedicalPolicyParam param) {
        if (!StringUtils.hasLength(param.getTitle())) {
            return Msg.fail().mess("标题不能为空");
        }
        if (!StringUtils.hasLength(param.getMessage())) {
            return Msg.fail().mess("内容不能为空");
        }
        if (param.getCityId() == null) {
            return Msg.fail().mess("城市不能为空");
        }
        return medicalPolicyService.updateMedicalPolicy(id, param);
    }

    /**
     * 根据id删除医保政策
     * @param id 医保政策id
     * @return 删除结果
     */
    @ApiOperation(value = "根据id删除医保政策", notes = "仅管理员可操作")
    @RolesAllowed({"1"})
    @DeleteMapping("/{id}")
    public Msg deleteMedicalPolicy(@PathVariable("id") Long id) {
        Msg msg = medicalPolicyService.deleteMedicalPolicy(id);
        return msg;
    }
}
