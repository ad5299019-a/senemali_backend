package com.senemali.api.dto.response;

import com.senemali.api.enums.StatutValidationFournisseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntrantResponse {
    private Long idIntrant;
    private String nomIntrant;
    private String typeIntrant;
    private String description;
    private int prix;
    private int quantite;
    private Boolean disponible;
    private StatutValidationFournisseur statutIntrant;
    private String imageUrl;
    private Long idFournisseur;
    private String nomFournisseur;
}
