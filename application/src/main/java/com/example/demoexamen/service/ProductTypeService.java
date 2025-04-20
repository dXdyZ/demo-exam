package com.example.demoexamen.service;

import com.example.demoexamen.entity.ProductType;
import com.example.demoexamen.exception.ProductTypeNotFoundException;
import com.example.demoexamen.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public ProductType getProductTypeByName(String name) {
        return productTypeRepository.findByTypeName(name).orElseThrow(
                () -> new ProductTypeNotFoundException(
                        String.format("Product by name: %s not found", name)
                )
        );
    }

    @Transactional
    public List<ProductType> getAllProductType() {
        return productTypeRepository.findAll();
    }

    public void saveProductType(List<ProductType> productTypes) {
        productTypeRepository.saveAll(productTypes);
    }
}
