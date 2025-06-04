package com.edu.lx.cms.controller;


import com.edu.lx.cms.domain.pojo.User;
import com.edu.lx.cms.service.UserService;
import com.edu.lx.cms.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author LiXue
 * @since 2025-06-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //TODO 检测用户名或密码为空错误
    @PostMapping("/login")
    public JsonResult getUser(@RequestBody User user) {
        return userService.getUser(user);
    }

    @PostMapping("/register")
    public JsonResult register(@RequestBody User user) {
        return userService.register(user);
    }

}
