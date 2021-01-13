package com.arh.springsecuritydemo.controller;

import com.arh.springsecuritydemo.entity.Menu;
import com.arh.springsecuritydemo.entity.Role;
import com.arh.springsecuritydemo.entity.User;
import com.arh.springsecuritydemo.entity.UserVo;
import com.arh.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * 不需要认证的url
 */
@Controller
@RequestMapping("/home")
public class NoAuthenticationController {

    @Autowired
    private UserService userService;

//    @ModelAttribute
//    public void testUseModel(Model model) {
//        model.addAttribute("test1", "testUseModel");
//    }
//
//    //如果不指定ModelAttribute的value值，则由返回对象类型隐含表示
//    @ModelAttribute("test2")
//    public String testUseModel2() {
//        return "testUseModel2";
//    }
//
//    @ModelAttribute("innerUser")
//    public User addUser() {
//        User user = new User();
//        user.setName("Jay");
//        user.setRemark("hello world");
//        return user;
//    }

    @RequestMapping("")
    public String userHome(Model model, @ModelAttribute("innerUser") User user) {
//        System.out.println(model.getAttribute("test1"));
//        System.out.println(model.getAttribute("test2"));
        System.out.println(user.getName());
//        System.out.println(newUser.getName());
        UserVo userVo = new UserVo();
        userVo.setName("Tom");
        model.addAttribute("userVo", userVo);
        return "home";
    }

    @RequestMapping("withParam")
    public String toHomeWithParam(Model model, @RequestParam(name = "info") String info) {
        UserVo userVo = new UserVo();
        userVo.setName("Tom");
        model.addAttribute("userVo", userVo);
        return "home";
    }

    @RequestMapping("/init")
    @ResponseBody
    public String insertData() {
        User user_tom = new User();
        user_tom.setName("管理员tom");
        user_tom.setUsername("arh");
        user_tom.setPassword("123");
        user_tom.setRemark("没什么备注 by tom");
        user_tom.setEnabled(0);

        User user_jay = new User();
        user_jay.setName("jay_jay");
        user_jay.setUsername("aj");
        user_jay.setPassword("123");
        user_jay.setRemark("没什么备注 by jay");
        user_jay.setEnabled(0);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setNameZh("管理员");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setNameZh("普通用户");

        Menu adminMenu = new Menu();
        adminMenu.setUrl("/admin/**");
        adminMenu.setName("管理员链接");

        Menu userMenu = new Menu();
        userMenu.setUrl("/user/**");
        userMenu.setName("普通用户链接");

        user_tom.addRole(adminRole);
        user_tom.addRole(userRole);

        user_jay.addRole(userRole);

        adminRole.addMenu(adminMenu);

        userRole.addMenu(userMenu);

        userService.initData(Arrays.asList(user_tom, user_jay), Arrays.asList(adminRole, userRole), Arrays.asList(adminMenu, userMenu));
        return "finish!!";
    }
}
