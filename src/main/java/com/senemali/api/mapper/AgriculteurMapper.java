package com.senemali.api.mapper;

import com.senemali.api.dto.request.AgriculteurRequest;
import com.senemali.api.dto.response.AgriculteurResponse;
import com.senemali.api.entity.Agriculteurs;
import org.springframework.stereotype.Component;

@Component
public class AgriculteurMapper {
    public void updateEntity(AgriculteurRequest request, Agriculteurs agriculteur) {
        agriculteur.setNom(request.getNom());
        agriculteur.setPrenom(request.getPrenom());
        agriculteur.setTelephone(request.getTelephone());
        agriculteur.setLocalisation(request.getLocalisation());
        agriculteur.setPhotoProfil(request.getPhotoProfil());
        agriculteur.setDescripion(request.getDescription());
    }

    public AgriculteurResponse toResponse(Agriculteurs agriculteur) {
        return AgriculteurResponse.builder()
                .idAgriculteur(agriculteur.getId())
                .id(agriculteur.getId())
                .nom(agriculteur.getNom())
                .prenom(agriculteur.getPrenom())
                .email(agriculteur.getEmail())
                .telephone(agriculteur.getTelephone())
                .localisation(agriculteur.getLocalisation())
                .photoProfil(agriculteur.getPhotoProfil())
                .description(agriculteur.getDescripion())
                .role(agriculteur.getRole())
                .statut(agriculteur.getStatut())
                .dateCreation(agriculteur.getDateCreation())
                .build();
    }
}