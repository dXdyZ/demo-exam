package com.example.demoexamen.service;

import com.example.demoexamen.entity.Partner;
import com.example.demoexamen.entity.Product;
import com.example.demoexamen.entity.SalesHistory;
import com.example.demoexamen.repository.SalesHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesHistoryService {
    private final SalesHistoryRepository salesHistoryRepository;
    private final ProductService productService;
    private final PartnerService partnerService;

    public SalesHistoryService(SalesHistoryRepository salesHistoryRepository, ProductService productService, PartnerService partnerService) {
        this.salesHistoryRepository = salesHistoryRepository;
        this.productService = productService;
        this.partnerService = partnerService;
    }

    @Transactional
    public void createSalesHistory(Long productId, Long partnerId, int quantityProducts) {
        salesHistoryRepository.save(
                SalesHistory.builder()
                        .product(productService.getProductById(productId))
                        .partner(partnerService.getPartnerById(partnerId))
                        .quantity(quantityProducts)
                        .salesDate(LocalDate.now())
                        .build()
        );
    }

    @Transactional
    public List<Product> getSalesProductsByPartnerId(Long partnerId) {
        return salesHistoryRepository.findAllByPartner_Id(partnerId).stream()
                .map(SalesHistory::getProduct)
                .toList();
    }

    @Transactional
    public void saveSalesHistories(List<SalesHistory> salesHistories) {
        salesHistoryRepository.saveAll(salesHistories);
    }

    @Transactional
    public List<SalesHistory> getAllSalesByPartners(List<Partner> partners) {
        return salesHistoryRepository.findAllByPartnerIn(partners);
    }

    @Transactional
    public List<SalesHistory> getAllSaleHistory() {
        return salesHistoryRepository.findAll();
    }

    public List<SalesHistory> getSaleHistoryByPartnerName(String partnerName) {
        return salesHistoryRepository.findAllByPartner_Name(partnerName);
    }
}








