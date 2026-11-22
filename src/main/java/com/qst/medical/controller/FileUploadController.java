package com.qst.medical.controller;

import com.qst.medical.service.FileUploadService;
import com.qst.medical.util.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;

/**
 * 文件上传控制器
 * <p>
 * 接收前端 form-data 中的 file 文件（药品图片），交由 FileUploadService 落盘，
 * 并把可访问的图片 URL 拼接好返回给前端（data.url）。
 * 图片访问由 WebSecurityConfig 中已放行的 /image/** 与 ImageResourceConfig 的静态资源映射提供。
 */
@Api(tags = "文件上传控制器")
@RestController
@RequestMapping("/api/base/upload")
@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 上传药品图片
     *
     * @param file 前端上传的图片文件（form-data 字段名 file）
     * @return 成功时 data 中 fileName 为文件名、url 为可直接访问的图片地址
     */
    @ApiOperation(value = "上传图片")
    @RolesAllowed({"1", "2"})
    @PostMapping(value = "")
    public Msg fileUpload(@RequestParam("file") MultipartFile file) {
        Msg msg = fileUploadService.upload(file);
        if (!msg.isSuccess()) {
            return msg;
        }
        // 依据当前请求的协议、主机、端口和上下文路径拼接图片URL，不写死 localhost:8080
        String fileName = String.valueOf(msg.getData().get("fileName"));
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(fileName)
                .toUriString();
        return msg.data("url", url);
    }
}
