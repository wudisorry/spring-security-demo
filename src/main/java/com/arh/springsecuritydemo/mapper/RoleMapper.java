package com.arh.springsecuritydemo.mapper;

import com.arh.springsecuritydemo.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    List<Role> selectAllRoles();

    List<Role> selectAllRolesWithMenu();

    @Insert("insert into t_role(f_name,f_name_zh) values(#{name},#{nameZh})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "f_id")
    int insert(Role role);
}
