package com.arh.springsecuritydemo.service;

import com.arh.springsecuritydemo.entity.Menu;
import com.arh.springsecuritydemo.entity.Role;
import com.arh.springsecuritydemo.entity.User;

import java.util.List;

public interface UserService {
    User queryUserById(Integer id);

    User queryUserByUsername(String username);

    User queryUserWithRoleAndMenuByUsername(String username);

    void initData(List<User> userList, List<Role> roleList, List<Menu> menuList);
}
