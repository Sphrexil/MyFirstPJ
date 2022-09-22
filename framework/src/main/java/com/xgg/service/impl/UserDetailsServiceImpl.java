package com.xgg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xgg.constants.SystemConstants;
import com.xgg.domain.entity.User;
import com.xgg.domain.entity.LoginUser;
import com.xgg.mapper.MenuMapper;
import com.xgg.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查询用户，如果没有就抛出异常
        if(Objects.isNull(user))
        {
            throw new RuntimeException("用户不存在");
        }
        //返回Userinfo

        if (SystemConstants.ADMIN.equals(user.getType())) {
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user, perms);
        }
        //TODO 查询权限信息封装

        return new LoginUser(user, null);
    }
}
