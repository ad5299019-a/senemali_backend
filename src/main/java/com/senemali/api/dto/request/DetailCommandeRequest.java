package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DetailCommandeRequest {
    private Long idProduit;
    private Long idIntrant;
    @NotNull @Positive private Integer quantite;
}
