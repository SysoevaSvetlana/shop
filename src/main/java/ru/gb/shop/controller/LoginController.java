package ru.gb.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {

            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {

        model.addAttribute("loginError", true);
        return "login";
    }
}