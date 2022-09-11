package com.xgg.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.LoginUser;
import com.xgg.domain.entity.User;
import com.xgg.domain.vo.AdminUserInfoVo;
import com.xgg.domain.vo.MenuVo;
import com.xgg.domain.vo.RoutersVo;
import com.xgg.domain.vo.UserInfoVo;
import com.xgg.enums.AppHttpCodeEnum;
import com.xgg.service.LoginService;
import com.xgg.service.MenuService;
import com.xgg.service.RoleService;
import com.xgg.utils.BeanCopyUtils;
import com.xgg.utils.JwtUtil;
import com.xgg.utils.RedisCache;
import com.xgg.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }




    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfo() {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        List<String> permissions = menuService.selectParmByUserId(loginUser.getUser().getId());
        List<String> roles =roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);

        AdminUserInfoVo user = new AdminUserInfoVo(permissions, roles, userInfoVo);

        return ResponseResult.okResult(user);
    }

    @Override
    public ResponseResult getRouters() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long id = loginUser.getUser().getId();
        List<MenuVo> menuVos = menuService.selectRouterMenuTreeByUserId(id);
        return ResponseResult.okResult(new RoutersVo(menuVos));
    }


}
