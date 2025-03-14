package com.example.admin.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.admin.common.result.Result;
import com.example.admin.common.result.Results;
import com.example.admin.dto.req.UserLoginReqDTO;
import com.example.admin.service.AuthService;
import com.example.admin.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nruonan
 * @description
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private AuthService authService;
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public SaResult login(@RequestBody UserLoginReqDTO requestParam) {
        return authService.login(requestParam);
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();
    }
}
