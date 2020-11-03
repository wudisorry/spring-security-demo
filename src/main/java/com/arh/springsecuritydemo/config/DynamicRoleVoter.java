package com.arh.springsecuritydemo.config;

import com.arh.springsecuritydemo.service.RoleService;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

public class DynamicRoleVoter implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        int result = ACCESS_ABSTAIN;
        //String url = ((FilterInvocation) object).getRequestUrl();
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);
        //attributes在DynamicFilterInvocationSecurityMetadataSource里已经根据数据库设置过了。
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;
                if (RoleService.ROLE_NOT_NEED.equalsIgnoreCase(attribute.getAttribute()) || RoleService.ROLE_NOT_MATCH.equalsIgnoreCase(attribute.getAttribute())) {
                    return ACCESS_GRANTED;
                }

                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }

    Collection<? extends GrantedAuthority> extractAuthorities(
            Authentication authentication) {
        return authentication.getAuthorities();
    }
}
