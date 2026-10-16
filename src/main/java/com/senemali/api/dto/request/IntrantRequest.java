package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IntrantRequest {
    @NotBlank private String nomIntrant;
    @NotBlank private String typeIntrant;
    private String description;
    @NotNull @Positive private Integer prix;
    @NotNull @Positive private Integer quantite;
    @NotBlank private String unite;
    @NotNull private Boolean disponible;
    private String imageUrl;
    @NotNull private Long idFournisseur;
}
