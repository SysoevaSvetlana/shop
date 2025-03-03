package ru.gb.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.shop.entity.ProductType;
import ru.gb.shop.repository.ProductTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductType> findAll() {
        return (List<ProductType>) productTypeRepository.findAll();
    }

    public Optional<ProductType> findById(Long id) {
        return productTypeRepository.findById(id);
    }
}
