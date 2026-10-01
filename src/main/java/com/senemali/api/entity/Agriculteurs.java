package com.senemali.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@Table(name = "agriculteurs")
@PrimaryKeyJoinColumn(name = "id_agriculteur", referencedColumnName = "id")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Agriculteurs extends Utilisateurs{
     private String descripion;

    @OneToMany(mappedBy = "agriculteur",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CultureAgriculteurs> culturesAgriculteurs;

    @OneToMany(mappedBy = "agriculteur")
    private List<ProduitAgricoles> produits;




}
