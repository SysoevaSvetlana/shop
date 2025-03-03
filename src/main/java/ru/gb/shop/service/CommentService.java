package ru.gb.shop.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.shop.entity.Comment;
import ru.gb.shop.entity.Product;
import ru.gb.shop.entity.User;
import ru.gb.shop.repository.CommentRepository;
import ru.gb.shop.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public Comment addComment(Long productId, String commentText, String username) {
        // Получаем текущего пользователя
        User currentUser = userService.findByUsername(username);

        // Получаем продукт
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));

        // Создаем новый комментарий
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setProduct(product);
        comment.setUser(currentUser);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));

        return commentRepository.findByProduct(product);
    }
}
