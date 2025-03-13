package ru.gb.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.shop.entity.Product;
import ru.gb.shop.entity.ProductType;
import ru.gb.shop.service.ProductService;
import ru.gb.shop.service.ProductTypeService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;



import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images/";

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    // Страница списка продуктов
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/product-list";
    }

    // Страница добавления продукта
    @GetMapping("/product/add")
    public String showAddProductForm(Model model) {
        List<ProductType> categories = productTypeService.findAll();

        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);

        return "admin/add-product";
    }

    // Обработка добавления продукта
    @PostMapping("/product/add")
    public String addProduct(
            @ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("productTypeId") Long productTypeId,
            Model model
    ) {
        try {
            // Обработка изображения
            if (!imageFile.isEmpty()) {
                String originalFilename = imageFile.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + fileExtension;

                Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(newFileName);
                Files.write(filePath, imageFile.getBytes());

                product.setImage(newFileName);
            }

            // Установка типа продукта
            ProductType productType = productTypeService.findById(productTypeId)
                    .orElseThrow(() -> new RuntimeException("Категория не найдена"));
            product.setProductType(productType);

            // Сохранение продукта
            productService.saveProduct(product);

            return "redirect:/admin/products?success";
        } catch (IOException e) {
            model.addAttribute("error", "Ошибка загрузки файла");
            return "admin/add-product";
        }
    }

    // Удаление продукта
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }



}

