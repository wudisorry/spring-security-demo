package com.arh.springsecuritydemo.mapper;

import com.arh.springsecuritydemo.entity.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper {
    @Insert("insert into t_user_role(f_user_id,f_role_id) values(#{userId},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "f_id")
    int insert(UserRole userRole);
}
