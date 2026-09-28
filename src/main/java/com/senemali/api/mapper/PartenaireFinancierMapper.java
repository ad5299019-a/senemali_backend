package com.senemali.api.mapper;

import com.senemali.api.dto.request.PartenaireFinancierRequest;
import com.senemali.api.dto.response.FinancementResponse;
import com.senemali.api.dto.response.PartenaireFinancierResponse;
import com.senemali.api.entity.PartenaireFinanciers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PartenaireFinancierMapper {
    public PartenaireFinanciers toEntity(PartenaireFinancierRequest request) {
        PartenaireFinanciers partenaire = new PartenaireFinanciers();
        updateEntity(request, partenaire);
        return partenaire;
    }

    public void updateEntity(PartenaireFinancierRequest request, PartenaireFinanciers partenaire) {
        partenaire.setNomPartenaire(request.getNomPartenaire());
        partenaire.setTypepartenaire(request.getTypepartenaire());
    }

    public PartenaireFinancierResponse toResponse(PartenaireFinanciers partenaire) {
        List<FinancementResponse> financements = partenaire.getFinancements() == null ? List.of()
                : partenaire.getFinancements().stream()
                .map(financement -> FinancementResponse.builder()
                        .idFinancement(financement.getIdFinancement())
                        .typeFinancement(financement.getTypeFinancement())
                        .conditionPrincipales(financement.getConditionPrincipales())
                        .montantMin(financement.getMontantMin())
                        .montantMax(financement.getMontantMax())
                        .disponible(financement.getDisponible())
                        .idPartenaire(partenaire.getIdPartenaire())
                        .nomPartenaire(partenaire.getNomPartenaire())
                        .build())
                .toList();
        return PartenaireFinancierResponse.builder()
                .idPartenaire(partenaire.getIdPartenaire())
                .nomPartenaire(partenaire.getNomPartenaire())
                .typepartenaire(partenaire.getTypepartenaire())
                .financements(financements)
                .build();
    }
}