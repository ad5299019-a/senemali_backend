package com.senemali.api.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "produit_agricoles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProduitAgricoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idProduit;

    @Column(nullable = false)
    private String nomProduit;


    private String description;

    @Column(nullable = false)
    private int quantite;

    @Column(nullable = false)
    private int prix;

    @Column(nullable = false)
    private String unite;

    @Column(nullable = false)
    private Boolean disponible;


    private String imageUrl;

    private LocalDateTime datePub;

    @ManyToOne
    @JoinColumn(name = "id_agriculteur", nullable = false)
    private Agriculteurs agriculteur;

    @OneToMany(mappedBy = "produit")
    private List<DetailCommandes> detailsCommandes;
    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateurs administrateur;
}
