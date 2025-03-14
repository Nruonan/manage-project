package com.example.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nruonan
 * @description
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/token")
    public String getToken() {
        return StpUtil.getTokenValue();
    }

    @GetMapping("/role")
    public String role(){
        List<String> roleList = StpUtil.getRoleList();
        return roleList == null ? "null" : roleList.toString();
    }
}
