package com.arh.springsecuritydemo.entity;

import java.io.Serializable;

public class UserVo implements Serializable {

    private String name;

    private String roleNames;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }
}
