package com.qst.medical.service;

import com.qst.medical.util.Msg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 文件上传业务类
 * <p>
 * 把前端上传的药品图片保存到 image.path 配置的目录（默认 ./images），
 * 文件名为 UUID + 图片后缀，避免同名覆盖；可访问 URL 由
 * FileUploadController 依据当前请求地址拼接，静态资源映射见 ImageResourceConfig。
 */
@Service
public class FileUploadService {

    /** 图片保存目录，可通过 application.yml 中的 image.path 覆盖，未配置时使用 ./images */
    @Value("${image.path:./images}")
    private String imagePath;

    /** 允许上传的图片后缀 */
    private static final List<String> ALLOWED_SUFFIX =
            Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");

    /**
     * 把上传的图片写入本地目录
     *
     * @param file 前端上传的图片文件
     * @return 成功时 data 中 fileName 为保存后的文件名；失败时返回失败信息
     */
    public Msg upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Msg.fail().mess("上传失败：请选择要上传的图片");
        }
        String suffix = getSuffix(file.getOriginalFilename());
        if (!ALLOWED_SUFFIX.contains(suffix)) {
            return Msg.fail().mess("上传失败：只支持 jpg、jpeg、png、gif、bmp、webp 格式的图片");
        }
        // UUID + 后缀：既避免同名文件互相覆盖，也避免中文、空格文件名带来的 URL 编码问题
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;
        File uploadDir = new File(imagePath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            return Msg.fail().mess("上传失败：图片保存目录创建失败");
        }
        File targetFile = new File(uploadDir, fileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return Msg.fail().mess("上传失败");
        }
        return Msg.success().mess("上传成功").data("fileName", fileName);
    }

    /**
     * 取文件名的后缀（小写，不含点）
     *
     * @param originalFilename 原始文件名
     * @return 后缀，取不到时返回空串
     */
    private String getSuffix(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        int index = originalFilename.lastIndexOf('.');
        if (index < 0 || index == originalFilename.length() - 1) {
            return "";
        }
        return originalFilename.substring(index + 1).toLowerCase(Locale.ROOT);
    }
}
