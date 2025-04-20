package com.example.demoexamen.repository;

import com.example.demoexamen.entity.Partner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerRepository extends CrudRepository<Partner, Long> {
    Optional<Partner> findByName(String name);

    List<Partner> findAll();
}
