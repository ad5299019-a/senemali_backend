package com.senemali.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Entité Administrateurs qui étend Utilisateurs.
 * L'admin gère la plateforme Senemali : valide les fournisseurs, publie les conseils, etc.
 */
@Data
@Entity
@Table(name = "administrateurs")
@PrimaryKeyJoinColumn(name = "id_administrateur", referencedColumnName = "id")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Administrateurs extends Utilisateurs {

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Financements> financements;

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestataires> prestataires;

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceAgricoles> serviceAgricoles;

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartenaireFinanciers> partenaireFinanciers;

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conseils> conseils;

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tutoriels> tutoriels;

    @OneToMany(mappedBy = "administrateurValidation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intrants> intrantsValides;

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProduitAgricoles> produitAgricoles;

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrixMarche> prixMarches;

    @OneToMany(mappedBy = "administrateurValidation")
    private List<Fournisseurs> fournisseursValides;
}
