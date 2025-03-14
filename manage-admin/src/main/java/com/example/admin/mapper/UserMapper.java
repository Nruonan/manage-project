package com.example.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nruonan
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2025-03-14 10:40:50
* @Entity com.example.admin.entity.UserDO.UserDO
*/
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

}




