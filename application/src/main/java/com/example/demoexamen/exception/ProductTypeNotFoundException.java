package com.example.demoexamen.exception;

public class ProductTypeNotFoundException extends RuntimeException {
    public ProductTypeNotFoundException(String message) {
        super(message);
    }
}
