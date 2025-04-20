package com.example.demoexamen.service;

import com.example.demoexamen.entity.Material;
import com.example.demoexamen.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public void saveMaterials(List<Material> materials) {
        materialRepository.saveAll(materials);
    }
}
