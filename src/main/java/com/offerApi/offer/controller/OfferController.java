/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.offerApi.offer.controller;

import com.offerApi.offer.models.Offer;
import com.offerApi.offer.repository.OfferRepository;
import com.offerApi.offer.validation.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author enzo
 */
@RestController
@RequestMapping("/api/offers")
@Validated
public class OfferController {

    private final OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Offer>>> getAllOffers() {
        List<Offer> offers = offerRepository.findAll();

        if (offers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay ofertas registradas");
        }

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Operación exitosa", offers)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Offer>> getOfferById(@PathVariable @NotNull @Min(1) Integer id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No se encontró ninguna oferta con el ID " + id));

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Operación exitosa", offer)
        );
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody @Valid Offer offer) {
        Offer saved = offerRepository.save(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Integer id, @RequestBody @Valid Offer updatedOffer) {
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
                    return ResponseEntity.ok(offerRepository.save(offer));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Offer>> partialUpdateOffer(@PathVariable Integer id, @RequestBody Offer partialOffer) {
    return offerRepository.findById(id)
            .map(existingOffer -> {
                BeanUtils.copyProperties(partialOffer, existingOffer, getNullPropertyNames(partialOffer));
                Offer updated = offerRepository.save(existingOffer);

                ApiResponse<Offer> response = new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Actualización parcial exitosa",
                        updated
                );
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> {
                ApiResponse<Offer> errorResponse = new ApiResponse<>(
                        HttpStatus.NOT_FOUND.value(),
                        "No se encontró la oferta con ID " + id,
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOffer(@PathVariable @NotNull @Min(1) Integer id) {
        if (!offerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró ninguna oferta con el ID " + id);
        }
        offerRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Eliminación completada", null));
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
