package com.example.demoexamen.service;

import com.example.demoexamen.entity.Partner;
import com.example.demoexamen.entity.SalesHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscountCalculatorService {
    private final SalesHistoryService salesHistoryService;

    public DiscountCalculatorService(SalesHistoryService salesHistoryService) {
        this.salesHistoryService = salesHistoryService;
    }

    @Transactional
    public Map<Partner, Integer> calculatePartnerDiscount(List<Partner> partners) {
        List<SalesHistory> salesHistories = salesHistoryService.getAllSalesByPartners(partners);
        Map<Partner, Integer> quantityRealizationProducts = new HashMap<>();
        for (SalesHistory salesHistory : salesHistories) {
            Integer quantityProducts = salesHistory.getQuantity();
            quantityRealizationProducts.merge(salesHistory.getPartner(), quantityProducts, Integer::sum);
        }
        partners.forEach(partner -> quantityRealizationProducts.putIfAbsent(partner, 0));
        Map<Partner, Integer> resultDiscount = new HashMap<>();
        quantityRealizationProducts.forEach((key, value) -> {
            if (value < 1000) resultDiscount.put(key, 0);
            if (value > 1000 && value < 5000) resultDiscount.put(key, 5);
            if (value > 5000 && value < 30000) resultDiscount.put(key, 10);
            if (value > 30000) resultDiscount.put(key, 15);
        });
        return resultDiscount;
    }
}






