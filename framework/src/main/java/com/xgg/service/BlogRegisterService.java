package com.xgg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.User;

public interface BlogRegisterService extends IService<User> {


    ResponseResult register(User user);
}
