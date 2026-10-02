package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class AuthRequest {
    @NotBlank(message = "Email ou numéro de téléphone requis")
    @Schema(description = "Email pour ADMIN ; email ou numéro de téléphone pour AGRICULTEUR, ACHETEUR et FOURNISSEUR")
    private String identifiant;
    @NotBlank(message = "Mot de passe requis")
    private String motDePasse;
}
