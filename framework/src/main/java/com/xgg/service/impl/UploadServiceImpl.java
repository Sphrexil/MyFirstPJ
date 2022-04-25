package com.xgg.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xgg.domain.ResponseResult;
import com.xgg.enums.AppHttpCodeEnum;
import com.xgg.handler.exception.SystemException;
import com.xgg.service.UploadService;
import com.xgg.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service("UploadService")
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
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
    private String uploadOss(MultipartFile imgFile, String filePath){




//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//        String accessKey = "TyfB_YrXJBqdnaFbG8vc9f76rfGpaWwJrYQTrRvB";
//        String secretKey = "LEobz6S20cKi3slHjYH_eEg7hVrKaAMLr9J7A2B5";
//        String bucket = "sphreixl";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            InputStream fileInputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fileInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://ra5x9rdf1.hn-bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "www.baidu.com";
    }
}
