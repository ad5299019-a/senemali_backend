package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CultureAgriculteurRequest {
    @NotNull private Long idAgriculteur;
    @NotNull private Long idCulture;
}
