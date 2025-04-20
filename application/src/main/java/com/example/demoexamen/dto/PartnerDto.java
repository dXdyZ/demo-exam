package com.example.demoexamen.dto;

public record PartnerDto (
        String name,
        String partnerType,
        String director,
        String email,
        String phoneNumber,
        String address,
        Long inn,
        Integer rating
) {}
