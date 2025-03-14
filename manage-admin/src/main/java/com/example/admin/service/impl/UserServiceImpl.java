package com.example.admin.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.dto.resp.UserRespDTO;
import com.example.admin.entity.UserDO;
import com.example.admin.mapper.UserMapper;
import com.example.admin.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author Nruonan
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2025-03-14 10:40:50
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO>
    implements UserService {

    @Override
    public UserRespDTO findUserByNameOrEmailOrPhone(String text) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
            .eq(UserDO::getUsername, text)
            .or()
            .eq(UserDO::getPhone, text)
            .or()
            .eq(UserDO::getMail, text);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null){
            return null;
        }else{
            return BeanUtil.toBean(userDO, UserRespDTO.class);
        }
    }
}




