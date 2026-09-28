package com.senemali.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdministrateurRequest {
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @Email @NotBlank private String email;
    @NotBlank private String telephone;
    @NotBlank private String localisation;
    private String photoProfil;
}