package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TutorielRequest {
    @NotBlank(message = "Le titre du tutoriel est requis")
    private String titreTutoriel;

    @NotBlank(message = "La description du tutoriel est requise")
    private String descriptionTutoriel;

    private String urlImage;
    private String urlVideo;

    @NotNull(message = "La catégorie est requise")
    private Long idCategorie;
}
