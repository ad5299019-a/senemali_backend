package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConseilRequest {
    @NotBlank private String titreConseil;
    @NotBlank private String contenu;
    @NotNull private Long idCulture;
}
