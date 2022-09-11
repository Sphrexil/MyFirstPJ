package com.xgg.service;

import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-04-06 17:10:56
 */
public interface LoginService {

    ResponseResult login(User user);

//    ResponseResult logout();

    ResponseResult getInfo();


    ResponseResult getRouters();

    ResponseResult logout();

}


