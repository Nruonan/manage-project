package com.example.admin.service.impl;

import cn.dev33.satoken.secure.SaBase64Util;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
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
        String password = SaBase64Util.decode(user.getPassword());
        if (!password.equals(requestParam.getPassword())) {
            throw new ServiceException("用户密码错误");
        }else{
            StpUtil.login(user.getId());
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return SaResult.data(tokenInfo);
        }
    }
}
