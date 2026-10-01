package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrixMarcheRequest {
    @NotBlank private String nomProduit;
    @NotNull @Positive private Integer prixProduit;
    @NotBlank private String unite;
    @NotBlank private String localisation;
}
