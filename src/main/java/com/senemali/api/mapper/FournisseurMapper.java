package com.senemali.api.mapper;

import com.senemali.api.dto.request.FournisseurRequest;
import com.senemali.api.dto.response.FournisseurResponse;
import com.senemali.api.entity.Fournisseurs;
import org.springframework.stereotype.Component;

@Component
public class FournisseurMapper {
    public void updateEntity(FournisseurRequest request, Fournisseurs fournisseur) {
        fournisseur.setNom(request.getNom());
        fournisseur.setPrenom(request.getPrenom());
        fournisseur.setTelephone(request.getTelephone());
        fournisseur.setLocalisation(request.getLocalisation());
        fournisseur.setPhotoProfil(request.getPhotoProfil());
        fournisseur.setDescription(request.getDescription());
        fournisseur.setNomEntrepise(request.getNomEntreprise());
        fournisseur.setZoneDeVente(request.getZoneDeVente());
    }

    public FournisseurResponse toResponse(Fournisseurs fournisseur) {
        return FournisseurResponse.builder()
                .idFournisseur(fournisseur.getIdFournisseur())
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .email(fournisseur.getEmail())
                .telephone(fournisseur.getTelephone())
                .localisation(fournisseur.getLocalisation())
                .photoProfil(fournisseur.getPhotoProfil())
                .description(fournisseur.getDescription())
                .nomEntreprise(fournisseur.getNomEntrepise())
                .zoneDeVente(fournisseur.getZoneDeVente())
                .statutFournisseur(fournisseur.getStatutFournisseur())
                .role(fournisseur.getRole())
                .statut(fournisseur.getStatut())
                .dateCreation(fournisseur.getDateCreation())
                .build();
    }
}