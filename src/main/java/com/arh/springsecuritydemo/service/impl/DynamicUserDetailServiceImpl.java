package com.arh.springsecuritydemo.service.impl;

import com.arh.springsecuritydemo.entity.User;
import com.arh.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
@ConditionalOnProperty(name = "condition.security.type", havingValue = "3", matchIfMissing = false)
@Transactional
public class DynamicUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //表中username做了唯一限制
        User user = userService.queryUserWithRoleAndMenuByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！请重新输入");
        }
        List<GrantedAuthority> authorityList = null;
        if (user.getRoleList() != null) {
            authorityList = user.getRoleList().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        } else {
            authorityList = AuthorityUtils.NO_AUTHORITIES;
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.isEnabledInBoolean(),
                true,
                true,
                true,
                authorityList);
    }
}
