package com.xgg.controller;

import com.xgg.annoation.SystemLog;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.User;
import com.xgg.service.BlogRegisterService;
import com.xgg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogRegisterService blogRegisterService;

    @GetMapping("/userInfo")
    @SystemLog(BusinessName = "更新用户信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult UpdateUserInfo(
            @RequestBody User user){
        return userService.UpdateUserInfo(user);
    }
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user)
    {
        return blogRegisterService.register(user);
    }
}
