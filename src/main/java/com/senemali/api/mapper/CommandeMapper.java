package com.senemali.api.mapper;

import com.senemali.api.dto.response.CommandeResponse;
import com.senemali.api.entity.Commandes;
import com.senemali.api.entity.DetailCommandes;
import com.senemali.api.entity.Utilisateurs;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandeMapper {
    private final DetailCommandeMapper detailCommandeMapper;

    public CommandeMapper(DetailCommandeMapper detailCommandeMapper) {
        this.detailCommandeMapper = detailCommandeMapper;
    }

    public CommandeResponse toResponse(Commandes commande) {
        Utilisateurs vendeur = getVendeur(commande);
        return CommandeResponse.builder()
                .idCommande(commande.getIdCommande())
                .dateCommande(commande.getDateCommande())
                .status(commande.getStatus())
                .montantTotal(commande.getMontantTotal())
                .idUtilisateur(commande.getUtilisateur() != null ? commande.getUtilisateur().getId() : null)
                .nomUtilisateur(commande.getUtilisateur() != null ? commande.getUtilisateur().getNom() : null)
                .prenomUtilisateur(commande.getUtilisateur() != null ? commande.getUtilisateur().getPrenom() : null)
                .telephoneUtilisateur(commande.getUtilisateur() != null ? commande.getUtilisateur().getTelephone() : null)
                .idVendeur(vendeur != null ? vendeur.getId() : null)
                .nomVendeur(vendeur != null ? vendeur.getNom() : null)
                .roleVendeur(vendeur != null ? vendeur.getRole().name() : null)
                .details(commande.getDetailCommandes() == null ? List.of()
                        : commande.getDetailCommandes().stream().map(detailCommandeMapper::toResponse).toList())
                .build();
    }

    private Utilisateurs getVendeur(Commandes commande) {
        if (commande.getDetailCommandes() == null || commande.getDetailCommandes().isEmpty()) {
            return null;
        }
        DetailCommandes detail = commande.getDetailCommandes().get(0);
        if (detail.getProduit() != null) {
            return detail.getProduit().getAgriculteur();
        }
        return detail.getIntrant() != null ? detail.getIntrant().getFournisseur() : null;
    }
}