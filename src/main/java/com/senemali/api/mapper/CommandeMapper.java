package com.senemali.api.mapper;

import com.senemali.api.dto.response.CommandeResponse;
import com.senemali.api.entity.Commandes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandeMapper {
    private final DetailCommandeMapper detailCommandeMapper;

    public CommandeMapper(DetailCommandeMapper detailCommandeMapper) {
        this.detailCommandeMapper = detailCommandeMapper;
    }

    public CommandeResponse toResponse(Commandes commande) {
        return CommandeResponse.builder()
                .idCommande(commande.getIdCommande())
                .dateCommande(commande.getDateCommande())
                .status(commande.getStatus())
                .montantTotal(commande.getMontantTotal())
                .idUtilisateur(commande.getUtilisateur() != null ? commande.getUtilisateur().getId() : null)
                .nomUtilisateur(commande.getUtilisateur() != null ? commande.getUtilisateur().getNom() : null)
                .details(commande.getDetailCommandes() == null ? List.of()
                        : commande.getDetailCommandes().stream().map(detailCommandeMapper::toResponse).toList())
                .build();
    }
}