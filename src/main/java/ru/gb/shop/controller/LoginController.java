package ru.gb.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class LoginController implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");}

    @GetMapping("/login-error")
    public String loginError(Model model) {

        return "login-error";
    }

//
//    @GetMapping("/login")
//    public String login(Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//
//            return "redirect:/index";
//        }
//        return "login";
//    }
//
//    @GetMapping("/login-error")
//    public String loginError(Model model) {
//
//        model.addAttribute("loginError", true);
//        return "login";
//    }
}