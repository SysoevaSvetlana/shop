package ru.gb.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.shop.service.CommentService;

import java.security.Principal;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // Добавление комментария только для авторизованных пользователей
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()") // Только для авторизованных
    public String addComment(
            @RequestParam Long productId,
            @RequestParam String commentText,
            Principal principal // Получаем текущего пользователя
    ) {
        commentService.addComment(productId, commentText, principal.getName());
        return "redirect:/product/" + productId;
    }
}

