package com.xgg.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.User;
import com.xgg.enums.AppHttpCodeEnum;
import com.xgg.handler.exception.SystemException;
import com.xgg.mapper.UserMapper;
import com.xgg.service.BlogRegisterService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogRegisterServiceImpl extends ServiceImpl<UserMapper, User> implements BlogRegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(User user) {

        //数据非空判断
        if(Objects.isNull(user.getUserName())&& !Strings.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(Objects.isNull(user.getPassword())&& !Strings.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(Objects.isNull(user.getNickName())&& !Strings.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(Objects.isNull(user.getEmail())&& !Strings.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //解析请求中的数据,对用户密码进行加密
        if(userNameExist(user.getUserName()))
        {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        //插入到数据库中
        return ResponseResult.okResult();
    }

    private boolean userNameExist(String userName) {

        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        int count = count(queryWrapper);
        return count>0;
    }
}
