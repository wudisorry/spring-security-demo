package com.arh.springsecuritydemo.config;

import com.arh.springsecuritydemo.service.UserService;
import com.arh.springsecuritydemo.service.impl.DynamicUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.Arrays;
import java.util.List;

@Configuration
@ConditionalOnProperty(name = "condition.security.type", havingValue = "3", matchIfMissing = false)
public class DynamicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private DynamicFilterInvocationSecurityMetadataSource dynamicFilterInvocationSecurityMetadataSource;

    /**
     * 源码中是根据class获取UserDetailsService的实例，并且BeanNames不能超过1
     * 所以我们在DynamicUserDetailServiceImpl类上加上@Service("userDetailsService")，这里不需要再返回了
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return dynamicUserDetailService;
//    }

    /**
     * 手动设置UserDetailsService的实例，就不需要管BeanNames数量了
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(dynamicUserDetailService);
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.printf("DynamicSecurityConfig里的passwordEncoder()");
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(dynamicFilterInvocationSecurityMetadataSource);
                        object.setAccessDecisionManager(accessDecisionManager());
                        return object;
                    }
                })
                .anyRequest().authenticated()
                .and()
                .formLogin(Customizer.withDefaults());

    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> voterList = Arrays.asList(
                new DynamicRoleVoter(),
                new WebExpressionVoter(),
                new AuthenticatedVoter()
        );
        return new UnanimousBased(voterList);
    }
}
