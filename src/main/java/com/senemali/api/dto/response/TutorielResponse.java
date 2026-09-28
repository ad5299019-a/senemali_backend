package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutorielResponse {
    private Long idTutoriel;
    private String titreTutoriel;
    private String descriptionTutoriel;
    private String urlImage;
    private String urlVideo;
    private Long idCategorie;
    private String nomCategorie;
    private Long idAdministrateur;
}
