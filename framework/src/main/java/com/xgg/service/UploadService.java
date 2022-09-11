package com.xgg.service;

import com.xgg.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img) throws IOException;

    ResponseResult uploadImgs(MultipartFile[] imgs);
}
