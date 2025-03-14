package com.example.admin.service.impl;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.admin.common.exception.ServiceException;
import com.example.admin.dto.req.UserLoginReqDTO;
import com.example.admin.dto.resp.UserRespDTO;
import com.example.admin.service.AuthService;
import com.example.admin.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Nruonan
 * @description
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private UserService userService;
    @Override
    public SaResult login(UserLoginReqDTO requestParam) {
        UserRespDTO user = userService.findUserByNameOrEmailOrPhone(requestParam.getUsername());
        if (user == null) {
            throw new ServiceException("用户不存在！");
        }
        if (!user.getPassword().equals(requestParam.getPassword())) {
            throw new ServiceException("用户密码！");
        }else{
            StpUtil.login(user.getId());
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return SaResult.data(tokenInfo);
        }
    }
}
