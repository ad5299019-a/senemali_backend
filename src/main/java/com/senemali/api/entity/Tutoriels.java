package com.senemali.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "tutoriels")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tutoriels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTutoriel;

    @Column(nullable = false)
    private String titreTutoriel;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descriptionTutoriel;

    @Column(length = 1000)
    private String urlImage;

    @Column(length = 1000)
    private String urlVideo;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateurs administrateur;

    @ManyToOne
    @JoinColumn(name = "id_categorie", nullable = false)
    private CategorieCultures categorie;
}
