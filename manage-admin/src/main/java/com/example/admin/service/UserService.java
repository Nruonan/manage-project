package com.example.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.resp.UserRespDTO;
import com.example.admin.entity.UserDO;

/**
* @author Nruonan
* @description 针对表【t_user】的数据库操作Service
* @createDate 2025-03-14 10:40:50
*/
public interface UserService extends IService<UserDO> {

    public UserRespDTO findUserByNameOrEmailOrPhone(String text);
}
