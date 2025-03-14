package com.example.admin.mapper;

import com.example.admin.dto.resp.UserRoleListRespDTO;
import com.example.admin.entity.UserRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author Nruonan
* @description 针对表【t_user_role】的数据库操作Mapper
* @createDate 2025-03-14 16:47:11
* @Entity generator.domain.TUserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    @Select("select role_id from t_user_role where user_id = #{id}")
    List<Integer> selectListById(Object id);
}




