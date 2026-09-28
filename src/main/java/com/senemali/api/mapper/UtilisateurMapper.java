package com.senemali.api.mapper;

import com.senemali.api.dto.request.UtilisateurRequest;
import com.senemali.api.dto.response.UtilisateurResponse;
import com.senemali.api.entity.Utilisateurs;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {
    public void updateEntity(UtilisateurRequest request, Utilisateurs utilisateur) {
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setTelephone(request.getTelephone());
        utilisateur.setLocalisation(request.getLocalisation());
        utilisateur.setPhotoProfil(request.getPhotoProfil());
    }

    public UtilisateurResponse toResponse(Utilisateurs utilisateur) {
        return UtilisateurResponse.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .telephone(utilisateur.getTelephone())
                .localisation(utilisateur.getLocalisation())
                .photoProfil(utilisateur.getPhotoProfil())
                .role(utilisateur.getRole())
                .statut(utilisateur.getStatut())
                .dateCreation(utilisateur.getDateCreation())
                .build();
    }
}