package com.senemali.api.entity;


import com.senemali.api.enums.StatutCommandeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "commandes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailCommandes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idDetail;

    @Column(nullable = false)
    private int quantite;

    @Column(nullable = false)
    private int prixUnitaire;

    @Column(nullable = false)
    private int montant;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private ProduitAgricoles produit;

    @ManyToOne
    @JoinColumn(name = "id_intrant")
    private Intrants intrant;

}
