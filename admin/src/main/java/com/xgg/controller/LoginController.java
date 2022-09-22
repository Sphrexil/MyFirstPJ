package com.xgg.controller;

import com.xgg.domain.ResponseResult;


import com.xgg.domain.entity.User;

import com.xgg.enums.AppHttpCodeEnum;
import com.xgg.handler.exception.SystemException;
import com.xgg.service.LoginService;
import com.xgg.service.MenuService;
import com.xgg.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;




@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logOut() {
        return loginService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo() {

        return loginService.getInfo();
    }
    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
//        selectRouterMenuTreeByUserId
        return loginService.getRouters();
    }
}




