package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProduitAgricoleRequest {
    @NotBlank private String nomProduit;
    private String description;
    @NotNull @Positive private Integer quantite;
    @NotNull @Positive private Integer prix;
    @NotBlank private String unite;
    @NotNull private Boolean disponible;
    private String imageUrl;
    @NotNull private Long idAgriculteur;
}
