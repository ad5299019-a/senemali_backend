package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TutorielRequest {
    @NotBlank private String titreTutoriel;
    @NotBlank private String descriptionTutoriel;
    @NotBlank private String urlImage;
    @NotBlank private String urlVideo;
    @NotNull private Long idCategorie;
}
