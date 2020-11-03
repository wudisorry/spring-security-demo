package com.arh.springsecuritydemo.service.impl;

import com.arh.springsecuritydemo.entity.Role;
import com.arh.springsecuritydemo.mapper.RoleMapper;
import com.arh.springsecuritydemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> queryAllRoles() {
        List<Role> result = roleMapper.selectAllRoles();
        return result;
    }

    @Override
    public List<Role> queryAllRolesWithMenu() {
        List<Role> result = roleMapper.selectAllRolesWithMenu();
        return result;
    }
}
