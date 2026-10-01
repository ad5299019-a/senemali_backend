package com.senemali.api.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prix_marches")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrixMarche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idPrix;

    @Column(nullable = false)
    private String nomProduit;


    @Column(nullable = false)
    private int prixProduit;

    @Column(nullable = false)
    private String unite;

    @Column(nullable = false)
    private String localisation;

    private LocalDateTime dateObserv;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateurs administrateur;
}
