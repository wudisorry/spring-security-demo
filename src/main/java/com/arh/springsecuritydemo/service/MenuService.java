package com.arh.springsecuritydemo.service;

import com.arh.springsecuritydemo.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> queryAllMenusWithRole();
}
