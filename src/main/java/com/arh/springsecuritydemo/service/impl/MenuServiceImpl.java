package com.arh.springsecuritydemo.service.impl;

import com.arh.springsecuritydemo.entity.Menu;
import com.arh.springsecuritydemo.mapper.MenuMapper;
import com.arh.springsecuritydemo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> queryAllMenusWithRole() {
        return menuMapper.selectAllMenusWithRole();
    }
}
