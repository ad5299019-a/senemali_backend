package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Email requis")
    private String email;
    @NotBlank(message = "Mot de passe requis")
    private String motDePasse;
}
