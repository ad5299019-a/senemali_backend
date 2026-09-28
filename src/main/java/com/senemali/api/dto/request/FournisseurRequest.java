package com.senemali.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FournisseurRequest {
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @Email @NotBlank private String email;
    @NotBlank private String telephone;
    @NotBlank private String localisation;
    @NotBlank private String motDePasse;
    private String photoProfil;
    private String description;
    private String nomEntreprise;
    private String zoneDeVente;
}
