package com.xgg.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.xgg.domain.ResponseResult;
import com.xgg.enums.AppHttpCodeEnum;
import com.xgg.handler.exception.SystemException;
import com.xgg.service.UploadService;
import com.xgg.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;

@Service("UploadService")
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    private String bucketName;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;



    @Override
    public ResponseResult uploadImg(MultipartFile img) throws IOException {
        //判断文件类型或者文件大小
        String originalFilename = img.getOriginalFilename();
        if(!originalFilename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img,filePath);
        //弱或判断通过上传文件到oss
        return ResponseResult.okResult(url);
    }
    private String uploadOss(MultipartFile imgFile, String filePath) throws IOException {

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

//        String fileName = filePath.split("/")[3];
        // 简单上传
        if (!Objects.isNull(imgFile)) {
            ossClient.putObject(
                    new PutObjectRequest(bucketName, filePath, imgFile.getInputStream()));
        }

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        String host =
                "https://" + bucketName + "." + endpoint;

        try {
                return host + "/" + key;
        } catch (Exception ex) {
            //ignore
        }
        return "www.baidu.com";
    }
}
