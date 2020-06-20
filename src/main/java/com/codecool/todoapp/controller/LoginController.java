package com.codecool.todoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

        @RequestMapping("/userlogin")
        public String loginPage() {
            return "login";
        }
}
