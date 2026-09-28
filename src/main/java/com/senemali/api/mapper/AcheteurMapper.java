package com.senemali.api.mapper;

import com.senemali.api.dto.request.AcheteursRequest;
import com.senemali.api.dto.response.AcheteurResponse;
import com.senemali.api.entity.Acheteurs;
import org.springframework.stereotype.Component;

@Component
public class AcheteurMapper {

    public void updateEntity(AcheteursRequest request, Acheteurs acheteur) {
        acheteur.setNom(request.getNom());
        acheteur.setPrenom(request.getPrenom());
        acheteur.setEmail(request.getEmail());
        acheteur.setTelephone(request.getTelephone());
        acheteur.setLocalisation(request.getLocalisation());
        acheteur.setPhotoProfil(request.getPhotoProfil());
    }

    public AcheteurResponse toResponse(Acheteurs acheteur) {
        return AcheteurResponse.builder()
                .idAcheteur(acheteur.getIdAcheteur())
                .id(acheteur.getId())
                .nom(acheteur.getNom())
                .prenom(acheteur.getPrenom())
                .email(acheteur.getEmail())
                .telephone(acheteur.getTelephone())
                .localisation(acheteur.getLocalisation())
                .photoProfil(acheteur.getPhotoProfil())
                .role(acheteur.getRole())
                .statut(acheteur.getStatut())
                .dateCreation(acheteur.getDateCreation())
                .build();
    }
}