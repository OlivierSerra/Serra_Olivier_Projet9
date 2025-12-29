package com.medilabo.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//affiche le login pour se connecter
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {

        return "login";
    }
}
