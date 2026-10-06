package com.senemali.api.entity;


import com.senemali.api.enums.TypePartenaireEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "partenaire_financiers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartenaireFinanciers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partenaire")
    private Long idPartenaire;

    @Column(nullable = false)
    private String nomPartenaire;

    @Enumerated(EnumType.STRING)
    private TypePartenaireEnum typepartenaire;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String localisation;

    @Column(nullable = false)
    private String description;

    private String imageUrl;

    @OneToMany(mappedBy = "partenaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Financements> financements;

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateurs administrateur;

}
