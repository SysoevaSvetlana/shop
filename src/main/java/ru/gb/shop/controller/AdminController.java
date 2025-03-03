package ru.gb.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.gb.shop.entity.Product;
import ru.gb.shop.entity.ProductType;
import ru.gb.shop.service.ProductService;
import ru.gb.shop.service.ProductTypeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    // Путь для сохранения изображений
    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images/";

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    // Страница добавления продукта
    @GetMapping("/product/add")
    public String showAddProductForm(Model model) {
        // Получаем список категорий для выпадающего списка
        List<ProductType> categories = productTypeService.findAll();

        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);

        return "admin/add-product";
    }

    // Обработка формы добавления продукта
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
                // Генерируем уникальное имя файла
                String originalFilename = imageFile.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + fileExtension;

                // Сохраняем файл
                Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(newFileName);
                Files.write(filePath, imageFile.getBytes());

                // Устанавливаем имя файла в сущность
                product.setImage(newFileName);
            }

            // Устанавливаем тип продукта
            ProductType productType = productTypeService.findById(productTypeId)
                    .orElseThrow(() -> new RuntimeException("Категория не найдена"));
            product.setProductType(productType);

            // Сохраняем продукт
            productService.saveProduct(product);

            return "redirect:/admin/product/add?success";
        } catch (IOException e) {
            model.addAttribute("error", "Ошибка загрузки файла");
            return "admin/add-product";
        }
    }

    // Список всех продуктов
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/product-list";
    }

    // Удаление продукта
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
