package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceAgricoleRequest {
    @NotBlank private String typeService;
    private String description;
    @NotBlank private String coutEstime;
    private String imageUrl;
    @NotNull private Boolean disponible;
    @NotNull private Long idPrestataire;
}
