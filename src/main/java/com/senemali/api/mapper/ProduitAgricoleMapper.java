package com.senemali.api.mapper;

import com.senemali.api.dto.request.ProduitAgricoleRequest;
import com.senemali.api.dto.response.ProduitAgricoleResponse;
import com.senemali.api.entity.ProduitAgricoles;
import org.springframework.stereotype.Component;

@Component
public class ProduitAgricoleMapper {
    public ProduitAgricoles toEntity(ProduitAgricoleRequest request) {
        ProduitAgricoles produit = new ProduitAgricoles();
        updateEntity(request, produit);
        return produit;
    }

    public void updateEntity(ProduitAgricoleRequest request, ProduitAgricoles produit) {
        produit.setNomProduit(request.getNomProduit());
        produit.setDescription(request.getDescription());
        produit.setQuantite(request.getQuantite());
        produit.setPrix(request.getPrix());
        produit.setUnite(request.getUnite());
        produit.setDisponible(request.getDisponible());
        produit.setImageUrl(request.getImageUrl());
    }

    public ProduitAgricoleResponse toResponse(ProduitAgricoles produit) {
        return ProduitAgricoleResponse.builder()
                .idProduit(produit.getIdProduit())
                .nomProduit(produit.getNomProduit())
                .description(produit.getDescription())
                .quantite(produit.getQuantite())
                .prix(produit.getPrix())
                .unite(produit.getUnite())
                .disponible(produit.getDisponible())
                .imageUrl(produit.getImageUrl())
                .datePub(produit.getDatePub())
                .idAgriculteur(produit.getAgriculteur() != null
                        ? produit.getAgriculteur().getIdAgriculteur() : null)
                .nomAgriculteur(produit.getAgriculteur() != null
                        ? produit.getAgriculteur().getNom() + " " + produit.getAgriculteur().getPrenom() : null)
                .build();
    }
}