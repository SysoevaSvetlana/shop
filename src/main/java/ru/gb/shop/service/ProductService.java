package ru.gb.shop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.shop.entity.Product;
import ru.gb.shop.entity.Role;
import ru.gb.shop.entity.User;
import ru.gb.shop.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public boolean saveProduct(Product product) {

        if (product.getId() == null) {

            productRepository.save(product);
            return true;
        }


        Optional<Product> productFromDB = productRepository.findById(product.getId());
        if (productFromDB.isPresent()) {

            return false;
        }


        productRepository.save(product);
        return true;
    }
    // Найти продукты по ID категории
    public List<Product> findProductsByCategory(Long categoryId) {
        return productRepository.findByProductType_Id(categoryId);
    }


    public boolean deleteProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
            return true;
        }
        return false;
    }


    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    // Найти продукт по ID
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }



    // Удалить продукт
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
