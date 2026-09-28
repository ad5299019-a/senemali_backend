package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailCommandeResponse {
    private Long idDetail;
    private int quantite;
    private int prixUnitaire;
    private int montant;
    private Long idProduit;
    private String nomProduit;
    private Long idIntrant;
    private String nomIntrant;
}
