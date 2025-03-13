package ru.gb.shop.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.gb.shop.entity.Role;
import ru.gb.shop.entity.User;
import ru.gb.shop.repository.RoleRepository;
import ru.gb.shop.service.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//@
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
//        // Проверка наличия ошибок валидации
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errors = new HashMap<>();
//            bindingResult.getFieldErrors().forEach(
//                    error -> errors.put(error.getField(), error.getDefaultMessage())
//            );
//            model.addAttribute("errors", errors);
//            return "registration";
//        }
//
//        // Проверка совпадения паролей
//        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
//            model.addAttribute("passwordError", "Пароли не совпадают");
//            return "registration";
//        }
//
//        // Проверка существования пользователя
//        if (userService.findByUsername(userForm.getUsername()) != null) {
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "registration";
//        }

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

