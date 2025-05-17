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
import jakarta.validation.constraints.*;
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

    @NotNull
    @Column(name = "id_type_offer", nullable = false)
    private Integer idTypeOffer;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(name = "date_open", nullable = false)
    private LocalDateTime dateOpen;
    
    @NotNull
    @Column(name = "date_close", nullable = false)
    private LocalDateTime dateClose;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @NotNull
    @Column(name = "id_user_create", nullable = false)
    private Integer idUserCreate;

    @NotNull
    @Column(name = "id_user_update", nullable = false)
    private Integer idUserUpdate;

    @NotNull
    @Column(name = "date_create", nullable = false)
    private LocalDateTime dateCreate;

    @NotNull
    @Column(name = "date_update", nullable = false)
    private LocalDateTime dateUpdate;
}
