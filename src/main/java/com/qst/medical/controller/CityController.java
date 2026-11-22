package com.qst.medical.controller;

import com.github.pagehelper.PageInfo;
import com.qst.medical.model.CityModel;
import com.qst.medical.service.CityService;
import com.qst.medical.util.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * 城市信息控制器
 *
 * @author qst
 */
@Api(tags = "城市信息控制器")
@RestController
@RequestMapping("/api/citys")
@CrossOrigin
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * 分页查询城市信息，name 不为空则按城市名或省份名模糊查询
     * 路径：/api/citys/{pn}/{size} 或 /api/citys
     *
     * @param pn   页码，可不传
     * @param size 每页大小，可不传
     * @param name 查询关键字，可不传
     * @return 统一响应体，分页信息放入 data 的 cityPageInfo 中
     */
    @ApiOperation("分页查询城市信息，name不为空则模糊查询")
    @GetMapping(value = {"/{pn}/{size}", ""})
    public Msg getCityWithPage(@PathVariable(value = "pn", required = false) Integer pn,
                               @PathVariable(value = "size", required = false) Integer size,
                               @RequestParam(value = "name", required = false) String name) {
        PageInfo<CityModel> info = cityService.getCityWithPage(pn, size, name);
        if (info != null) {
            return Msg.success().data("cityPageInfo", info);
        }
        return Msg.fail();
    }

    /**
     * 根据 id 查询一个城市信息
     *
     * @param id 城市 id
     * @return 统一响应体
     */
    @ApiOperation("根据id查询城市信息")
    @GetMapping("{id}")
    public Msg getCityById(@PathVariable("id") Integer id) {
        Msg msg = cityService.getCityById(id);
        return msg;
    }

    /**
     * 新增一个城市信息
     *
     * @param cityNumber 城市编号
     * @return 统一响应体，城市已存在时返回状态码 10004
     */
    @ApiOperation("新增城市信息")
    @RolesAllowed({"1"})
    @PostMapping(value = "")
    public Msg saveCity(@RequestParam(value = "cityNumber", required = false) Integer cityNumber) {
        if (cityNumber == null) {
            return Msg.fail().mess("城市编号不能为空");
        }
        if (cityService.checkCityByName(cityNumber) > 0) {
            return Msg.fail().mess("城市已经存在").code(10004);
        }
        return cityService.saveCity(cityNumber);
    }

    /**
     * 根据 id 删除城市
     *
     * @param id 城市 id
     * @return 统一响应体
     */
    @ApiOperation("根据id删除城市信息")
    @RolesAllowed({"1"})
    @DeleteMapping("{id}")
    public Msg deleteCityById(@PathVariable("id") Integer id) {
        Msg msg = cityService.deleteCityById(id);
        return msg;
    }
}
