package com.example.demoexamen.repository;

import com.example.demoexamen.entity.Partner;
import com.example.demoexamen.entity.SalesHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalesHistoryRepository extends CrudRepository<SalesHistory, Long> {
    List<SalesHistory> findAllByPartner_Id(Long partnerId);
    List<SalesHistory> findAllByPartnerIn(Collection<Partner> partners);
    List<SalesHistory> findAll();
    List<SalesHistory> findAllByPartner_Name(String partnerName);
}
