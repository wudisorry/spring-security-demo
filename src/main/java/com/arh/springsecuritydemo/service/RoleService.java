package com.arh.springsecuritydemo.service;

import com.arh.springsecuritydemo.entity.Role;

import java.util.List;

public interface RoleService {

    //菜单没有匹配任何角色
    String ROLE_NOT_MATCH = "ROLE_NOTMATCH";

    //数据库中没有该菜单信息，也就没角色可以配置
    String ROLE_NOT_NEED = "ROLE_NOTNEED";

    List<Role> queryAllRoles();

    List<Role> queryAllRolesWithMenu();

}
