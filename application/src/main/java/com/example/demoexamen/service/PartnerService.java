package com.example.demoexamen.service;

import com.example.demoexamen.dto.PartnerDto;
import com.example.demoexamen.entity.Partner;
import com.example.demoexamen.exception.PartnerNotFoundException;
import com.example.demoexamen.repository.PartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public void savePartner(PartnerDto partner) {
        partnerRepository.save(Partner.builder()
                        .name(partner.name())
                        .partnerType(partner.partnerType())
                        .director(partner.director())
                        .email(partner.email())
                        .phoneNumber(partner.phoneNumber())
                        .address(partner.address())
                        .inn(partner.inn())
                        .rating(partner.rating())
                .build());
    }

    public Partner getPartnerById(Long id) throws PartnerNotFoundException{
        return partnerRepository.findById(id).orElseThrow(
                () -> new PartnerNotFoundException(String.format("Partner by id: %s not found", id))
        );
    }

    public Partner getPartnerByName(String name) throws PartnerNotFoundException{
        return partnerRepository.findByName(name).orElseThrow(
                () -> new PartnerNotFoundException(String.format("Partner by name: %s not found", name))
        );
    }

    @Transactional
    public Partner updatePartnerData(Long id, PartnerDto partnerDto) throws PartnerNotFoundException{
        Partner partner = getPartnerById(id);
        if (!partnerDto.name().isBlank()) {
            partner.setName(partnerDto.name());
        }
        if (!partnerDto.partnerType().isBlank()) {
            partner.setPartnerType(partnerDto.partnerType());
        }
        if (!partnerDto.director().isBlank()) {
            partner.setDirector(partnerDto.director());
        }
        if (!partnerDto.email().isBlank()) {
            partner.setEmail(partnerDto.email());
        }
        if (!partnerDto.phoneNumber().isBlank()) {
            partner.setPhoneNumber(partnerDto.phoneNumber());
        }
        if (!partnerDto.address().isBlank()) {
            partner.setAddress(partnerDto.address());
        }
        if (partnerDto.inn() != null) {
            partner.setInn(partnerDto.inn());
        }
        if (partnerDto.rating() != null) {
            partner.setRating(partnerDto.rating());
        }
        return partnerRepository.save(partner);
    }

    @Transactional
    public List<Partner> getAllPartner() {
        return partnerRepository.findAll();
    }

    @Transactional
    public void savePartners(List<Partner> partners) {
        partnerRepository.saveAll(partners);
    }
}









