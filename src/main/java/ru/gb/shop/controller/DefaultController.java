package ru.gb.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.shop.entity.Product;
import ru.gb.shop.entity.ProductType;
import ru.gb.shop.repository.ProductRepository;
import ru.gb.shop.repository.ProductTypeRepository;

//import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class DefaultController {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<ProductType> types = productTypeRepository.findAll();
        Map<ProductType, List<Product>> map = new HashMap<>();
        types.forEach(type -> map.put(type, productRepository.findByProductType(type)));
        model.addAttribute("map", map);
        return "index";
    }

    @GetMapping("/product")
    public String product(@RequestParam("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "product";
    }

//    @GetMapping("/productTypeList")
//    public String productTypeList(Model model) {
//        Iterable<ProductType> types = productTypeRepository.findAll();
//        model.addAttribute("types", types);
//        return "productTypeList";
//    }
//
//    @GetMapping("productTypeList/add")
//    public String productTypeListAdd(Model model) {
//        ProductType productType = new ProductType();
//        model.addAttribute("productType", productType);
//        return "productTypeForm";
//    }
//
//    @PostMapping("productTypeList/add")
//    public String productTypeListAddSubmit(@ModelAttribute ProductType productType, Model model){
//        productTypeRepository.save(productType);
//        model.addAttribute("types", productTypeRepository.findAll());
//        return "productTypeList";
//    }
//
//    @GetMapping("/productTypeList/delete/{productTypeId}")
//    public String productTypeListDelete(@PathVariable("productTypeId") long id, Model model) {
//        productTypeRepository.deleteById(id);
//        model.addAttribute("types", productTypeRepository.findAll());
//        return "productTypeList";
//    }
//
//    @GetMapping("/productTypeList/edit/{productTypeId}")
//    public String productTypeListEdit(@PathVariable("productTypeId") long id, Model model) {
//        ProductType productType = productTypeRepository.findById(id).orElse(null);
//        model.addAttribute("productType", productType);
//        return "productTypeForm";
//    }
}