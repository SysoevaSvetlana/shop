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



    public boolean saveProduct (Product product) {
        Optional<Product> productFromDB = productRepository.findById(product.getId());

        if (productFromDB != null) {
            return false;
        }

        //user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        productRepository.save(product);
        return true;
    }

    public boolean deleteProduct(Long productId) {
        if (productRepository.findById(productId).isPresent()) {
            productRepository.deleteById(productId);
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

//    // Сохранить продукт
//    public Product save(Product product) {
//        return productRepository.save(product);
//    }

    // Удалить продукт
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
