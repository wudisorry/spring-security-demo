package com.arh.springsecuritydemo.mapper;

import com.arh.springsecuritydemo.entity.MenuRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRoleMapper {

    @Insert("insert into t_menu_role(f_menu_id,f_role_id) values(#{menuId},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "f_id")
    int insert(MenuRole menuRole);
}
