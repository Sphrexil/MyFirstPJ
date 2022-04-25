package com.xgg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.User;
import com.xgg.domain.vo.UserInfoVo;
import com.xgg.mapper.UserMapper;
import com.xgg.utils.BeanCopyUtils;
import com.xgg.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.xgg.service.UserService;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-04-10 15:41:07
 */
@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        //读取当前用户信息
        User user = getById(SecurityUtils.getUserId());
        UserInfoVo vo= BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //根据用户id查询用户信息
        //封装成成userInfo
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult UpdateUserInfo(User user) {


        //读取当前用户信息
        updateById(user);

        //根据用户id修改用户信息
        //封装成userInfo
        return ResponseResult.okResult();

    }
}
