package com.qst.medical.controller;

import com.github.pagehelper.PageInfo;
import com.qst.medical.domain.DrugCompany;
import com.qst.medical.service.CompanyService;
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
 * 医药公司信息控制器
 * <p>
 * 接口路径统一以 /api/companys 开头。
 */
@Api(tags = "医药公司信息控制器")
@RestController
@RequestMapping("/api/companys")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 医药公司信息的分页查询，name 不为空则模糊查询
     *
     * @param pn   页码，可省略
     * @param size 页面大小，可省略
     * @param name 公司名关键字，可省略
     * @return 成功返回 data 中 key 为 pageInfo 的分页信息
     */
    @ApiOperation(value = "医药公司信息的分页查询", notes = "name不为空则按公司名模糊查询，pn、size可省略")
    @GetMapping(value = {"/{pn}/{size}", ""})
    public Msg getCompanyWithPage(@PathVariable(value = "pn", required = false) Integer pn,
                                  @PathVariable(value = "size", required = false) Integer size,
                                  @RequestParam(required = false) String name) {
        PageInfo<DrugCompany> info = companyService.getCompanyWithPage(pn, size, name);
        if (info != null) {
            return Msg.success().data("pageInfo", info);
        }
        return Msg.fail();
    }

    /**
     * 根据id查询一个医药公司信息
     *
     * @param id 公司id
     * @return 成功返回 data 中 key 为 company 的公司信息
     */
    @ApiOperation(value = "根据id查询一个医药公司信息")
    @GetMapping("{id}")
    public Msg getCompanyById(@PathVariable("id") Integer id) {
        Msg msg = companyService.getCompanyById(id);
        return msg;
    }

    /**
     * 添加一个医药公司信息，仅管理员（角色1）可操作
     *
     * @param company 医药公司对象
     * @return 成功返回 data 中 key 为 pages 的页码信息
     */
    @ApiOperation(value = "添加一个医药公司信息", notes = "仅管理员可操作，公司名与公司电话不能为空")
    @RolesAllowed({"1"})
    @PostMapping(value = "")
    public Msg saveCompany(@RequestBody DrugCompany company) {
        String name = company.getCompanyName();
        String phone = company.getCompanyPhone();

        if (name == null || phone == null || "".equals(name) || "".equals(phone)) {
            return Msg.fail().mess("填写信息不完整");
        }
        return companyService.saveCompany(company);
    }

    /**
     * 根据id更新医药公司信息，仅管理员（角色1）可操作
     *
     * @param id      公司id
     * @param company 医药公司对象
     * @return 成功返回“修改成功”
     */
    @ApiOperation(value = "根据id更新医药公司信息", notes = "仅管理员可操作，公司名与公司电话不能为空")
    @RolesAllowed({"1"})
    @PutMapping(value = "/{id}")
    public Msg updateCompanyById(@PathVariable("id") Long id, @RequestBody DrugCompany company) {
        String name = company.getCompanyName();
        String phone = company.getCompanyPhone();
        if (name == null || "".equals(name)) {
            return Msg.fail().mess("公司名称不能为空");
        }
        if (phone == null || "".equals(phone)) {
            return Msg.fail().mess("公司电话不能为空");
        }
        Msg msg = companyService.updateCompanyById(id, company);
        return msg;
    }

    /**
     * 根据id删除医药公司信息，仅管理员（角色1）可操作
     *
     * @param id 公司id
     * @return 成功返回“删除成功”
     */
    @ApiOperation(value = "根据id删除医药公司信息", notes = "仅管理员可操作")
    @RolesAllowed({"1"})
    @DeleteMapping("{id}")
    public Msg deleteCompanyById(@PathVariable("id") Integer id) {
        Msg msg = companyService.deleteCompanyById(id);
        return msg;
    }
}
