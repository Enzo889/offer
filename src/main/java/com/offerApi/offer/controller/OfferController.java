/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.offerApi.offer.controller;

import com.offerApi.offer.models.Offer;
import com.offerApi.offer.repository.OfferRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author enzo
 */

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    
    private final OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Offer getOfferById(@PathVariable Integer id) {
        return offerRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Offer createOffer(@RequestBody Offer offer) {
        return offerRepository.save(offer);
    }

    @PutMapping("/{id}")
    public Offer updateOffer(@PathVariable Integer id, @RequestBody Offer updatedOffer) {
        return offerRepository.findById(id)
                .map(offer -> {
                    offer.setIdTypeOffer(updatedOffer.getIdTypeOffer());
                    offer.setName(updatedOffer.getName());
                    offer.setDateOpen(updatedOffer.getDateOpen());
                    offer.setDateClose(updatedOffer.getDateClose());
                    offer.setDescription(updatedOffer.getDescription());
                    offer.setIdUserCreate(updatedOffer.getIdUserCreate());
                    offer.setIdUserUpdate(updatedOffer.getIdUserUpdate());
                    offer.setDateCreate(updatedOffer.getDateCreate());
                    offer.setDateUpdate(updatedOffer.getDateUpdate());
                    return offerRepository.save(offer);
                })
                .orElse(null);
    }

    
    @PatchMapping("/{id}")
    public ResponseEntity<Offer> partialUpdateOffer(@PathVariable Integer id, @RequestBody Offer partialOffer) {
        return offerRepository.findById(id)
                .map(existingOffer -> {
                    BeanUtils.copyProperties(partialOffer, existingOffer, getNullPropertyNames(partialOffer));
                    return ResponseEntity.ok(offerRepository.save(existingOffer));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Integer id) {
        offerRepository.deleteById(id);
    }

   // Método auxiliar para obtener nombres de propiedades nulas
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();
        for (var pd : src.getPropertyDescriptors()) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}
