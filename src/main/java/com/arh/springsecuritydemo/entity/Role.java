package com.arh.springsecuritydemo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Role implements Serializable {
    private Integer id;
    private String name;
    private String nameZh;
    private List<Menu> menuList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public void addMenu(Menu menu) {
        if (menuList == null) {
            menuList = new ArrayList<>();
        }
        menuList.add(menu);
    }
}
