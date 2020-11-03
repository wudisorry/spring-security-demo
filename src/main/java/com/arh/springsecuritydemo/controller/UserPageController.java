package com.arh.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserPageController {

    @RequestMapping("/login")
    public String login(){
        System.out.println("come in /login");
        return "login";
    }
}
