package com.senemali.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "conseils")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conseils {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idConseil;

    @Column(nullable = false)
    private String titreConseil;

    @Column(nullable = false)
    private  String contenu;

    private LocalDate datePub;

    @ManyToOne
    @JoinColumn(name = "id_culture", nullable = false)
    private Cultures culture;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateurs administrateur;
}
