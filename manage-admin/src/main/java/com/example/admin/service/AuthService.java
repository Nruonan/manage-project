package com.example.admin.service;

import cn.dev33.satoken.util.SaResult;
import com.example.admin.dto.req.UserLoginReqDTO;

/**
 * @author Nruonan
 * @description
 */
public interface AuthService {

    SaResult login(UserLoginReqDTO requestParam);
}
