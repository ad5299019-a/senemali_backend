package com.senemali.api.entity;


import com.senemali.api.enums.StatutValidationFournisseur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "intrants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Intrants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idIntrant;

    @Column(nullable = false)
    private String nomIntrant;

    @Column(nullable = false)
    private String typeIntrant;


    private String description;

    @Column(nullable = false)
    private int prix;

    @Column(nullable = false)
    private Boolean disponible;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutValidationFournisseur statutIntrant;


    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "id_fournisseur", nullable = false)
    private Fournisseurs fournisseur;

    @OneToMany(mappedBy = "intrant")
    private List<DetailCommandes> detailsCommandes;

    @ManyToOne
    @JoinColumn(name = "id_administrateur_validation", nullable = false)
    private Administrateurs administrateurValidation;
}
