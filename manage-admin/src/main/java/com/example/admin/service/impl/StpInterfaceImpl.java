package com.example.admin.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.admin.entity.RoleDO;
import com.example.admin.mapper.RoleMapper;
import com.example.admin.mapper.UserRoleMapper;
import com.example.admin.service.UserRoleService;
import com.example.admin.service.UserService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * @author Nruonan
 * @description
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserRoleMapper mapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        Object id =  StpUtil.getLoginId();
        List<Integer> roleIdList = mapper.selectListById(id);
        if (roleIdList != null && roleIdList.size() > 0) {
            List<RoleDO> roleDOS = roleMapper.selectList(
                Wrappers.lambdaQuery(RoleDO.class).in(RoleDO::getId, roleIdList));
            return roleDOS.stream().map(RoleDO::getName).toList();
        }
        return null;
    }
}
