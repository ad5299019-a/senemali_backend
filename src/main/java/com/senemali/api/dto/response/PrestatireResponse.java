package com.senemali.api.dto.response;

import com.senemali.api.enums.TypePrestataireEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrestatireResponse {
    private Long idPrestataire;
    private String nomPrestataire;
    private TypePrestataireEnum typePrestataire;
    private String description;
    private String specialite;
    private String telephone;
    private String localisation;
    private String imageUrl;
    private List<ServiceAgricoleResponse> services;
}
