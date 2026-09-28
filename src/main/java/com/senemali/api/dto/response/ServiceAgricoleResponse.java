package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAgricoleResponse {
    private Long idService;
    private String typeService;
    private String description;
    private String coutEstime;
    private String imageUrl;
    private Boolean disponible;
    private Long idPrestataire;
    private String nomPrestataire;
}
