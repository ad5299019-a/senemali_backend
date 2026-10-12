package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TutorielRequest {
    @NotBlank(message = "Le titre du tutoriel est requis")
    private String titreTutoriel;

    @NotBlank(message = "La description du tutoriel est requise")
    private String descriptionTutoriel;

    private String urlImage;
    private String urlVideo;

    /** ID de la catégorie concernée, ou null si le tutoriel concerne toutes les catégories */
    private Long idCategorie;
}
