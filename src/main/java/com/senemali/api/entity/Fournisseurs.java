package com.senemali.api.entity;

import com.senemali.api.enums.StatutValidationFournisseur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@Table(name = "fournisseurs")
@PrimaryKeyJoinColumn(name = "id_fournisseur", referencedColumnName = "id")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Fournisseurs extends Utilisateurs{
    private  String description;
    private  String nomEntrepise;
    private  String zoneDeVente;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutValidationFournisseur statutFournisseur;

    @OneToMany(mappedBy = "fournisseur",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intrants> intrants;

    @ManyToOne
    @JoinColumn(name = "id_administrateur_validation")
    private Administrateurs administrateurValidation;
}
