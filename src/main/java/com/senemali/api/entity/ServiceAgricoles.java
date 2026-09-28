package com.senemali.api.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "service_agricole")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceAgricoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idService;

    @Column(nullable = false)
    private String typeService;


    private String description;

    @Column(nullable = false)
    private String coutEstime;


    private String imageUrl;

    @Column(nullable = false)
    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "id_prestataire", nullable = false)
    private Prestataires prestataire;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateurs administrateur;


}
