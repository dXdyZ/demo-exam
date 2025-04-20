package com.example.demoexamen.repository;

import com.example.demoexamen.entity.ProductType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {
    Optional<ProductType> findByTypeName(String typeName);
    List<ProductType> findAll();
}
