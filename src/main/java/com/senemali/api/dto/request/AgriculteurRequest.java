package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgriculteurRequest {
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @NotBlank private String telephone;
    @NotBlank private String localisation;
    private String photoProfil;
    private String description;
}
