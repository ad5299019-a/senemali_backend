package com.senemali.api.mapper;

import com.senemali.api.dto.request.TutorielRequest;
import com.senemali.api.dto.response.TutorielResponse;
import com.senemali.api.entity.Tutoriels;
import org.springframework.stereotype.Component;

@Component
public class TutorielMapper {
    public Tutoriels toEntity(TutorielRequest request) {
        Tutoriels tutoriel = new Tutoriels();
        updateEntity(request, tutoriel);
        return tutoriel;
    }

    public void updateEntity(TutorielRequest request, Tutoriels tutoriel) {
        tutoriel.setTitreTutoriel(request.getTitreTutoriel());
        tutoriel.setDescriptionTutoriel(request.getDescriptionTutoriel());
        tutoriel.setUrlImage(request.getUrlImage());
        tutoriel.setUrlVideo(request.getUrlVideo());
    }

    public TutorielResponse toResponse(Tutoriels tutoriel) {
        return TutorielResponse.builder()
                .idTutoriel(tutoriel.getIdTutoriel())
                .titreTutoriel(tutoriel.getTitreTutoriel())
                .descriptionTutoriel(tutoriel.getDescriptionTutoriel())
                .urlImage(tutoriel.getUrlImage())
                .urlVideo(tutoriel.getUrlVideo())
                .idCategorie(tutoriel.getCategorie() != null ? tutoriel.getCategorie().getIdCategorie() : null)
                .nomCategorie(tutoriel.getCategorie() != null ? tutoriel.getCategorie().getNomCategorie() : null)
                .idAdministrateur(tutoriel.getAdministrateur() != null
                        ? tutoriel.getAdministrateur().getIdAdmistrateur() : null)
                .build();
    }
}