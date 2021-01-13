package com.arh.springsecuritydemo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@ConditionalOnProperty(name = "condition.security.type", havingValue = "2", matchIfMissing = false)
public class MultiHttpSecurityConfig {

    /**
     * 使用InMemoryUserDetailsManager
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //在开发阶段获取密码加密后的字符串，在上线阶段删除明文代码，把pwd的地方改成加密后的字符串
        String pwd = passwordEncoder.encode("123");
        System.out.println(pwd);

        UserDetails userDetails = User.withUsername("user").password(pwd).roles("user").build();
        UserDetails userDetails2 = User.withUsername("admin").password(pwd).roles("admin", "user").build();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(userDetails);
        manager.createUser(userDetails2);
        return manager;
    }

    /**
     * 使用JdbcUserDetailsManager
     * 如果不重新setUsersByUsernameQuery()，就要求表结构符合默认
     * @return
     */
//    public UserDetailsService userDetailsService(){
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//        return jdbcUserDetailsManager;
//    }

    /**
     * url以api开头时，用的security模块
     * order注解保证了ApiWebSecurityConfigurerAdapter的顺序，没有order注解则默认在最后
     * 注意直接调用了HttpSecurity.antMatcher()
     */
    @Configuration
    @ConditionalOnProperty(name = "condition.security.type", havingValue = "2", matchIfMissing = false)
    @Order(1)
    public static class ApiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                    .authorizeRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                            .anyRequest().hasRole("admin")
                    )
                    .httpBasic(Customizer.withDefaults());
        }
    }

    @Configuration
    @ConditionalOnProperty(name = "condition.security.type", havingValue = "2", matchIfMissing = false)
    public static class FormWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    //关闭csrf使得/home/**下的post请求不需要验证
                    .csrf().disable()
                    .authorizeRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                            .antMatchers("/home/**", "/public/**").permitAll()
                            .antMatchers("/user/**").access("hasRole('user') or hasRole('admin')")
                            .antMatchers("/admin/**").hasRole("admin")
                            .anyRequest().authenticated()
                    )
                    .formLogin(Customizer.withDefaults());
        }
    }

}
