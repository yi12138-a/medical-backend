package com.qst.medical.controller;

import com.qst.medical.param.CompanyPolicyParam;
import com.qst.medical.service.CompanyPolicyService;
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
 * 医药公司政策控制器类
 * <p>
 * 接口路径统一以 /api/company_policys 开头。
 */
@Api(tags = "医药公司政策控制器类")
@RestController
@RequestMapping("/api/company_policys")
@CrossOrigin
public class CompanyPolicyController {

    @Autowired
    private CompanyPolicyService companyPolicyService;

    /**
     * 分页、关键字查询医药公司政策信息
     *
     * @param param 医药公司政策查询参数（pn、size、keyword）
     * @return 成功返回 data 中 key 为 policyInfo 的分页信息
     */
    @ApiOperation(value = "分页、关键字查询医药公司政策信息",
            notes = "pn为当前页、size为每页大小、keyword为查询关键字，均可省略")
    @GetMapping(value = "")
    public Msg getPolicyWithPage(CompanyPolicyParam param) {
        Msg msg = companyPolicyService.getAllPolicyWithPage(param);
        return msg;
    }

    /**
     * 添加医药公司政策信息，仅管理员（角色1）可操作
     *
     * @param param 医药公司政策参数
     * @return 成功返回 data 中 key 为 pages 的页码信息
     */
    @ApiOperation(value = "添加医药公司政策信息", notes = "仅管理员可操作")
    @RolesAllowed({"1"})
    @PostMapping(value = "")
    public Msg saveMedicalPolicy(@RequestBody CompanyPolicyParam param) {
        Msg msg = companyPolicyService.savePolicy(param);
        return msg;
    }

    /**
     * 更新医药公司政策信息，仅管理员（角色1）可操作
     *
     * @param id    医药公司政策id
     * @param param 医药公司政策参数
     * @return 成功返回“修改成功”
     */
    @ApiOperation(value = "更新医药公司政策信息", notes = "仅管理员可操作，标题、内容、公司ID不能为空")
    @RolesAllowed({"1"})
    @PutMapping(value = "/{id}")
    public Msg updateMedicalPolicy(@PathVariable("id") Long id, @RequestBody CompanyPolicyParam param) {
        if (!StringUtils.hasLength(param.getTitle())) {
            return Msg.fail().mess("标题不能为空");
        }
        if (!StringUtils.hasLength(param.getMessage())) {
            return Msg.fail().mess("内容不能为空");
        }
        if (param.getCompanyId() == null) {
            return Msg.fail().mess("公司ID不能为空");
        }
        return companyPolicyService.updatePolicy(id, param);
    }

    /**
     * 根据id删除医药公司政策，仅管理员（角色1）可操作
     *
     * @param id 医药公司政策id
     * @return 成功返回“删除成功”
     */
    @ApiOperation(value = "根据id删除医药公司政策", notes = "仅管理员可操作")
    @RolesAllowed({"1"})
    @DeleteMapping("/{id}")
    public Msg deletePolicy(@PathVariable("id") Long id) {
        return companyPolicyService.deletePolicy(id);
    }
}
