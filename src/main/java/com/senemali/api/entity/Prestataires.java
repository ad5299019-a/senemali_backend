package com.senemali.api.entity;


import com.senemali.api.enums.TypePrestataireEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "prestataires")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prestataires {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestataire")
    private Long idPrestataire;

    @Column(nullable = false)
    private String nomPrestataire;

    @Enumerated(EnumType.STRING)
    private TypePrestataireEnum typePrestataire;


    private String description;

    @Column(nullable = false)
    private String specialite;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String localisation;


    private String imageUrl;

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceAgricoles> services;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateurs administrateur;


}
