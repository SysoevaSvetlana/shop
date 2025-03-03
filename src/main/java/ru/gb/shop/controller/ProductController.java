package ru.gb.shop.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.shop.entity.Comment;
import ru.gb.shop.entity.Product;
import ru.gb.shop.service.CommentService;
import ru.gb.shop.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public String showProductDetails(
            @PathVariable Long id,
            Model model
    ) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));

        List<Comment> comments = commentService.getCommentsByProduct(id);

        model.addAttribute("product", product);
        model.addAttribute("comments", comments);

        return "product";
    }
}


