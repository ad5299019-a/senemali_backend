package com.senemali.api.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "financements")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Financements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idFinancement;

    @Column(nullable = false)
    private String typeFinancement;


    private String conditionPrincipales;

    @Column(nullable = false)
    private int montantMin;

    @Column(nullable = false)
    private int montantMax;

    private int imageUrl;

    @Column(nullable = false)
    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "id_partenaire", nullable = false)
    private PartenaireFinanciers partenaire;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateurs administrateur;
}
