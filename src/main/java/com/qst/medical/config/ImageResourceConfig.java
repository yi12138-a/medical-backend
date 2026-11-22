package com.qst.medical.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * 图片静态资源访问配置
 * <p>
 * 把 /image/** 映射到本地图片目录（image.path，默认 ./images），
 * 这样 FileUploadService 保存的图片可以通过
 * http://主机:端口/image/文件名 直接预览。
 * <p>
 * 说明：规格要求在 application.properties 中配置
 * spring.web.resources.static-locations 与 upload.path，
 * 但本项目使用 application.yml，且 Coding Convention 约定不修改已有 config 目录下的公共配置，
 * 因此这里新增独立的配置类完成同样的映射，目录通过 image.path 读取（未配置时默认 ./images）。
 */
@Configuration
public class ImageResourceConfig implements WebMvcConfigurer {

    /** 图片保存/访问目录，可通过 application.yml 中的 image.path 覆盖 */
    @Value("${image.path:./images}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 转换为绝对路径的 file: URI，并保证以 / 结尾（Spring 才能按目录解析相对资源名）
        String location = Paths.get(imagePath).toAbsolutePath().normalize().toUri().toString();
        if (!location.endsWith("/")) {
            location = location + "/";
        }
        registry.addResourceHandler("/image/**")
                .addResourceLocations(location);
    }
}
