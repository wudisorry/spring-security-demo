package com.arh.springsecuritydemo.config;

import com.arh.springsecuritydemo.entity.Menu;
import com.arh.springsecuritydemo.entity.Role;
import com.arh.springsecuritydemo.service.MenuService;
import com.arh.springsecuritydemo.service.RoleService;
import com.arh.springsecuritydemo.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "condition.security.type", havingValue = "3", matchIfMissing = false)
public class DynamicFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menuList = menuService.queryAllMenusWithRole();
        if (menuList != null) {
            for (Menu menu : menuList) {
                if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                    List<Role> roleList = menu.getRoleList();
                    if (roleList != null && roleList.size() > 0) {
                        List<String> roleStrList = roleList.stream().map(Role::getName).collect(Collectors.toList());
                        return SecurityConfig.createList(roleStrList.toArray(new String[roleStrList.size()]));
                    }
                }
            }
            return SecurityConfig.createList(RoleServiceImpl.ROLE_NOT_NEED);
        } else {
            return SecurityConfig.createList(RoleServiceImpl.ROLE_NOT_MATCH);
        }

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<Role> roleList = roleService.queryAllRoles();
        if (roleList != null && roleList.size() > 0) {
            List<String> roleStrList = roleList.stream().map(Role::getName).collect(Collectors.toList());
            return SecurityConfig.createList(roleStrList.toArray(new String[roleStrList.size()]));
        }
        return SecurityConfig.createList(RoleServiceImpl.ROLE_NOT_MATCH);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
