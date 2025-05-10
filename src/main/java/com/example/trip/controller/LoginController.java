package com.example.trip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "https://kauth.kakao.com/oauth/authorize")
public class LoginController {

    @GetMapping("/jo_login")
    public String logins() {
        return "login/jo_login";
    }

}
