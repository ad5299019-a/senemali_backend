package com.senemali.api.entity;


import com.senemali.api.enums.StatutCommandeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "commandes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Commandes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idCommande;

    @Column(nullable = false)
    private LocalDateTime dateCommande;

    @Enumerated(EnumType.STRING)
    private StatutCommandeEnum status;

    @Column(nullable = false)
    private int montantTotal;

    @OneToMany(
            mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailCommandes> detailCommandes;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateurs utilisateur;

}
