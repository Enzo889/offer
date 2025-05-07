/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.offerApi.offer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author enzo
 */

@Entity
@Table(name = "offer")
@Data
public class Offer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_offer")
    private Integer idOffer;

    @Column(name = "id_type_offer", nullable = false)
    private Integer idTypeOffer;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "date_open", nullable = false)
    private LocalDateTime dateOpen;

    @Column(name = "date_close", nullable = false)
    private LocalDateTime dateClose;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "id_user_create", nullable = false)
    private Integer idUserCreate;

    @Column(name = "id_user_update", nullable = false)
    private Integer idUserUpdate;

    @Column(name = "date_create", nullable = false)
    private LocalDateTime dateCreate;

    @Column(name = "date_update", nullable = false)
    private LocalDateTime dateUpdate;
}
