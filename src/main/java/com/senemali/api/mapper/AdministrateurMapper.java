package com.senemali.api.mapper;

import com.senemali.api.dto.request.AdministrateurRequest;
import com.senemali.api.dto.response.AdministrateurResponse;
import com.senemali.api.entity.Administrateurs;
import org.springframework.stereotype.Component;

@Component
public class AdministrateurMapper {
    public void updateEntity(AdministrateurRequest request, Administrateurs administrateur) {
        administrateur.setNom(request.getNom());
        administrateur.setPrenom(request.getPrenom());
        administrateur.setEmail(request.getEmail());
        administrateur.setTelephone(request.getTelephone());
        administrateur.setLocalisation(request.getLocalisation());
        administrateur.setPhotoProfil(request.getPhotoProfil());
    }

    public AdministrateurResponse toResponse(Administrateurs administrateur) {
        return AdministrateurResponse.builder()
                .idAdministrateur(administrateur.getId())
                .id(administrateur.getId())
                .nom(administrateur.getNom())
                .prenom(administrateur.getPrenom())
                .email(administrateur.getEmail())
                .telephone(administrateur.getTelephone())
                .localisation(administrateur.getLocalisation())
                .photoProfil(administrateur.getPhotoProfil())
                .role(administrateur.getRole())
                .statut(administrateur.getStatut())
                .dateCreation(administrateur.getDateCreation())
                .build();
    }
}