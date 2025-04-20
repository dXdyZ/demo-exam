package com.example.demoexamen.service;

import com.example.demoexamen.entity.Product;
import com.example.demoexamen.entity.ProductType;
import com.example.demoexamen.exception.ProductNotFoundException;
import com.example.demoexamen.exception.ProductTypeNotFoundException;
import com.example.demoexamen.repository.ProductRepository;
import com.example.demoexamen.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(String.format("Product by id: %s not found", id))
        );
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(
                () -> new ProductNotFoundException(String.format("Product by name: %s not found", name))
        );
    }

    @Transactional
    public void saveProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

    @Transactional
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}









