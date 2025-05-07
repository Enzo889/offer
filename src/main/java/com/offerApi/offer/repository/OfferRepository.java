/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.offerApi.offer.repository;

import com.offerApi.offer.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author enzo
 */
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    
}
