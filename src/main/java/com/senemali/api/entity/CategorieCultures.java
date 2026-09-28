package com.senemali.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "Categorie_Cultures")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorieCultures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idCategorie;

    @Column(nullable = false)
    private String nomCategorie;

    private String description;

    @OneToMany(mappedBy = "categorie",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Cultures> cultures;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Tutoriels> tutoriels;
}
