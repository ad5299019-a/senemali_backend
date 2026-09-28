package com.senemali.api.dto.request;

import com.senemali.api.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @Email @NotBlank private String email;
    @NotBlank private String telephone;
    @NotBlank private String localisation;
    @NotBlank private String motDePasse;
    @NotNull private RoleEnum role;
    private String photoProfil;

    // Champs spécifiques Agriculteur
    private String descriptionAgriculteur;
    // Champs spécifiques Fournisseur

    private String descriptionFournisseur;
    private String nomEntreprise;
    private String zoneDeVente;
}
