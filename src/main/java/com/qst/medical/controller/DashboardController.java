package com.qst.medical.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qst.medical.service.DashboardService;
import com.qst.medical.util.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * 主页面数据面板控制器
 * <p>
 * 接口路径统一以 /api/dashboard 开头。
 */
@Api(tags = "数据面板控制器类")
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 获取主页面数据面板所需的统计数据
     *
     * @return 成功返回 data 中 key 为 data 的 Json 对象
     */
    @ApiOperation(value = "获取主页面数据面板数据", notes = "仅管理员（1）和医师（2）可访问")
    @RolesAllowed({"1", "2"})
    @GetMapping(value = "")
    public Msg getDashboardController() {
        ObjectNode node = dashboardService.getDashboardData();
        return Msg.success().data("data", node);
    }
}
