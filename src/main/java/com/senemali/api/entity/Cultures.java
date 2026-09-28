package com.senemali.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "cultures")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cultures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCulture;

    @Column(nullable = false)
    private String nomCulture;

    private  String description;

    @ManyToOne
    @JoinColumn(name = "id_categorie",nullable = false)
    private CategorieCultures categorie;

    @OneToMany(mappedBy = "culture")
    private List<CultureAgriculteurs> culturesAgriculteurs;

    @OneToMany(mappedBy = "culture",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conseils> conseils;
}
