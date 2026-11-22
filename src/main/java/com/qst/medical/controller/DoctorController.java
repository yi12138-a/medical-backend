package com.qst.medical.controller;

import com.qst.medical.param.DoctorParam;
import com.qst.medical.service.DoctorService;
import com.qst.medical.util.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
 * 医师控制器类
 * 提供医师分页查询、医师级别与诊治类型查询、医师添加、修改、删除及重置密码接口
 */
@Api(tags = "医师控制器类")
@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * 分页查询医师信息，支持 keyword 关键字过滤
     */
    @ApiOperation(value = "分页查询医师信息")
    @RolesAllowed({"1", "2"})
    @GetMapping(value = "")
    public Msg getDoctorWithPage(DoctorParam param) {
        return doctorService.getDoctorWithPage(param);
    }

    /**
     * 查询所有的医师级别和诊治类型
     */
    @ApiOperation(value = "查询所有医师级别和诊治类型")
    @RolesAllowed({"1", "2"})
    @GetMapping("/info")
    public Msg getLevelAndType() {
        return doctorService.getLevelAndType();
    }

    /**
     * 添加医师（同时创建该医师对应的账号）
     */
    @ApiOperation(value = "添加医师信息")
    @RolesAllowed({"1", "2"})
    @PostMapping(value = "")
    public Msg saveDoctor(@RequestBody @Validated DoctorParam param) {
        return doctorService.saveDoctor(param);
    }

    /**
     * 修改医师信息（同时更新该医师对应的账号）
     */
    @ApiOperation(value = "修改医师信息")
    @RolesAllowed({"1"})
    @PutMapping("/{id}")
    public Msg updateDoctor(@PathVariable("id") Long id, @RequestBody DoctorParam param) {
        return doctorService.updateDoctor(id, param);
    }

    /**
     * 删除医师（同时删除该医师对应的账号）
     */
    @ApiOperation(value = "删除医师信息")
    @RolesAllowed({"1"})
    @DeleteMapping("/{id}")
    public Msg deleteDoctor(@PathVariable("id") Long id) {
        return doctorService.deleteDoctorById(id);
    }

    /**
     * 重置医师账号密码为默认密码 666666
     */
    @ApiOperation(value = "重置医师账号密码")
    @RolesAllowed({"1"})
    @PutMapping("/reset/{id}")
    public Msg resetPwd(@PathVariable("id") Long id) {
        return doctorService.resetPwd(id);
    }
}
