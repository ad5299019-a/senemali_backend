package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancementResponse {
    private Long idFinancement;
    private String typeFinancement;
    private String conditionPrincipales;
    private int montantMin;
    private int montantMax;
    private Boolean disponible;
    private Long idPartenaire;
    private String nomPartenaire;
    private Long idAdministrateur;
}
