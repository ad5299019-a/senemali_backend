package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FinancementRequest {
    @NotBlank private String typeFinancement;
    private String conditionPrincipales;
    @NotNull @Positive private Integer montantMin;
    @NotNull @Positive private Integer montantMax;
    @NotNull private Boolean disponible;
    @NotNull private Long idPartenaire;
}
