package com.xgg.controller;

import com.xgg.domain.ResponseResult;
import com.xgg.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * description: UpLoadController
 * date: 2022/9/11 20:57
 * author: zhenyu
 * version: 1.0
 */
@RestController
@RequestMapping("/upload")
public class UpLoadController {

    @Autowired
    private UploadService uploadService;
    @PostMapping
    public ResponseResult uploadPic(@RequestPart("img") MultipartFile multipartFile) throws IOException {
        return uploadService.uploadImg(multipartFile);
    }

}
