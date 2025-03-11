package ru.gb.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.shop.entity.Product;
import ru.gb.shop.entity.ProductType;
import ru.gb.shop.repository.ProductRepository;
import ru.gb.shop.repository.ProductTypeRepository;
import ru.gb.shop.service.ProductService;
import ru.gb.shop.service.ProductTypeService;

//import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
public class DefaultController {

    @Autowired
    ProductTypeService productTypeService;


    @Autowired
    private ProductService productService;


    @Autowired
    ProductRepository productRepository;

//        @GetMapping({"/", "/index"})
//    public String index(Model model, Principal principal) {
//        Iterable<ProductType> types = productTypeRepository.findAll();
//        Map<ProductType, List<Product>> map = new HashMap<>();
//        types.forEach(type -> map.put(type, productRepository.findByProductType(type)));
//
//        model.addAttribute("map", map);
//
//        // Добавляем информацию о пользователе
//        if (principal != null) {
//            model.addAttribute("username", principal.getName());
//        }
//
//        return "index";
//    }
    @GetMapping({"/", "/index"})
    public String index(
            @RequestParam(required = false) Long categoryId,
            Model model,
            Principal principal
    ) {
        List<ProductType> categories = productTypeService.findAll();


        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }

        // Если выбрана категория, фильтруем продукты
        List<Product> products = (categoryId != null)
                ? productService.findProductsByCategory(categoryId)
                : productService.findAll();

        // Группируем продукты по категориям
        Map<ProductType, List<Product>> groupedProducts = groupProductsByCategory(products);

        model.addAttribute("categories", categories);
        model.addAttribute("map", groupedProducts);
        model.addAttribute("selectedCategoryId", categoryId); // Для выделения выбранной категории

        return "index";
    }

    // Группировка продуктов по категориям
    private Map<ProductType, List<Product>> groupProductsByCategory(List<Product> products) {
        Map<ProductType, List<Product>> groupedProducts = new LinkedHashMap<>();

        for (Product product : products) {
            ProductType category = product.getProductType();
            groupedProducts.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
        }

        return groupedProducts;
    }


}