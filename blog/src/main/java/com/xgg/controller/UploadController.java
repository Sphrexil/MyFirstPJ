package com.xgg.controller;

import com.xgg.domain.ResponseResult;
import com.xgg.domain.vo.ArticleImgVo;
import com.xgg.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.text.resources.FormatData;

import java.io.IOException;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img) throws IOException {

        return uploadService.uploadImg(img);
    }

    @PostMapping("/upload/articlePictures")
    public ResponseResult uploadImgs(MultipartFile[] img) throws IOException {

        return uploadService.uploadImgs(img);
    }
    @DeleteMapping("/upload/delete/articlePictures")
    public ResponseResult deleteImgs(@RequestBody String[] imgs) {
        return  uploadService.deleteImgs(imgs);
    }
}
