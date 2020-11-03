package com.arh.springsecuritydemo.service.impl;

import com.arh.springsecuritydemo.entity.*;
import com.arh.springsecuritydemo.mapper.*;
import com.arh.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public User queryUserById(Integer id) {
        User user = userMapper.selectUserById(id);
        return user;
    }

    @Override
    public User queryUserByUsername(String username) {
        User user = userMapper.selectUserByUsername(username);
        return user;
    }

    @Override
    public User queryUserWithRoleAndMenuByUsername(String username) {
        User user = userMapper.selectUserWithRoleAndMenuByUsername(username);
        return user;
    }

    @Override
    public void initData(List<User> userList, List<Role> roleList, List<Menu> menuList) {
        for (Menu menu : menuList) {
            menuMapper.insert(menu);
        }
        for (Role role : roleList) {
            roleMapper.insert(role);
            List<Menu> currentMenuList = role.getMenuList();
            for (Menu menu : currentMenuList) {
                MenuRole menuRole = new MenuRole();
                menuRole.setMenuId(menu.getId());
                menuRole.setRoleId(role.getId());
                menuRoleMapper.insert(menuRole);
            }
        }
        for (User user : userList) {
            userMapper.insert(user);
            List<Role> currentRoleList = user.getRoleList();
            for (Role role : currentRoleList) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(role.getId());
                userRoleMapper.insert(userRole);
            }
        }
    }
}
