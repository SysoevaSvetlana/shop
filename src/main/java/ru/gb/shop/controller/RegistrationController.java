package ru.gb.shop.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.gb.shop.entity.Role;
import ru.gb.shop.entity.User;
import ru.gb.shop.repository.RoleRepository;
import ru.gb.shop.service.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;


    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping
    public String registerUser(
            @Valid @ModelAttribute("userForm") User userForm,
            BindingResult bindingResult,
            Model model
    ) {
        // Проверка наличия ошибок валидации
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );
            model.addAttribute("errors", errors);
            return "registration";
        }

        // Проверка совпадения паролей
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }

        // Проверка существования пользователя
        if (userService.findByUsername(userForm.getUsername()) != null) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        // Установка роли по умолчанию
        Role userRole = roleRepository.findByName("ROLE_USER");

        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
        userForm.setRoles(Collections.singleton(userRole));


        try {
            // Сохранение пользователя
            userService.saveUser(userForm);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("registrationError", "Ошибка регистрации: " + e.getMessage());
            return "registration";
        }
    }
}

//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.gb.shop.entity.User;
//import ru.gb.shop.service.UserService;
//
//@Controller
//@RequestMapping("/registration")
//public class RegistrationController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @GetMapping
//    public String registrationPage(Model model) {
//        model.addAttribute("userForm", new User());
//        return "registration";
//    }
//
//    @PostMapping
//    public String registerUser(
//            @Valid @ModelAttribute("userForm") User userForm,
//            BindingResult bindingResult,
//            Model model
//    ) {
//        // Валидация формы
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//
//        // Проверка совпадения паролей
//        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
//            model.addAttribute("passwordError", "Пароли не совпадают");
//            return "registration";
//        }
//
//        // Хеширование пароля
//        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
//
//        // Установка роли по умолчанию
//        Role userRole = roleRepository.findByName("ROLE_USER");
//        userForm.setRoles(Collections.singleton(userRole));
//
//        try {
//            // Сохранение пользователя
//            userService.saveUser(userForm);
//            return "redirect:/login";
//        } catch (Exception e) {
//            model.addAttribute("registrationError", "Ошибка регистрации");
//            return "registration";
//        }
//    }



//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import ru.gb.shop.entity.User;
//import ru.gb.shop.service.UserService;
//
//@Controller
//public class RegistrationController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String addUser(@ModelAttribute("userForm")  User userForm, BindingResult bindingResult, Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
//            model.addAttribute("passwordError", "Пароли не совпадают");
//            return "registration";
//        }
//        if (!userService.saveUser(userForm)){
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "registration";
//        }
//
//        return "redirect:/";
//    }
//}