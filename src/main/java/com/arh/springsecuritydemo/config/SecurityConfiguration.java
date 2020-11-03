package com.arh.springsecuritydemo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@ConditionalOnProperty(name = "condition.security.type", havingValue = "1", matchIfMissing = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * passwordEncoder()红色线时因为@Configuration被注释了
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("configure方法里的对象是：" + passwordEncoder());
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("123")).roles("user")
                .and()
                .withUser("admin").password(passwordEncoder().encode("123")).roles("admin", "user");

        //auth.userDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.printf("passwordEncoder方法产出的对象是：" + bCryptPasswordEncoder);
        return bCryptPasswordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home/**", "/public/**").permitAll()
                .antMatchers("/user/**").access("hasRole('user') or hasRole('admin')")
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated()
                //可以在里面打断点用于看上面配置的授权，因为是后处理所以顺序无所谓
                //泛型FilterSecurityInterceptor是固定的，因为在执行postProcess方法前会检查泛型类型和我要后处理的Bean类型上是否有关联
                //如何知道泛型类型，需要查看该方法调用者对象的源码，看create出来的哪个filter
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource = object.getSecurityMetadataSource();
                        System.out.println(filterInvocationSecurityMetadataSource.getAllConfigAttributes());
                        return object;
                    }
                })
                .and()
                .formLogin();
//                .httpBasic(Customizer.withDefaults());//和httpBasic()一样

//                .formLogin(form->form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/custom/login")//如果我们自定义的登录页面的post请求链接改了，这里需要同步更改。这样UsernamePasswordAuthenticationFilter就能处理这个请求
//                        .permitAll());
//                .and()
//                .httpBasic();

//        http.authorizeRequests()
//                .anyRequest().permitAll();

    }

}
