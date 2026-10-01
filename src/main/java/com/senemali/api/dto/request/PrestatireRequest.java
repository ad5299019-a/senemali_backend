package com.senemali.api.dto.request;

import com.senemali.api.enums.TypePrestataireEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrestatireRequest {
    @NotBlank private String nomPrestataire;
    @NotNull private TypePrestataireEnum typePrestataire;
    private String description;
    @NotBlank private String specialite;
    @NotBlank private String telephone;
    @NotBlank private String localisation;
    private String imageUrl;
}
