package com.senemali.api.mapper;

import com.senemali.api.dto.request.FinancementRequest;
import com.senemali.api.dto.response.FinancementResponse;
import com.senemali.api.entity.Financements;
import org.springframework.stereotype.Component;

@Component
public class FinancementMapper {
    public Financements toEntity(FinancementRequest request) {
        Financements financement = new Financements();
        updateEntity(request, financement);
        return financement;
    }

    public void updateEntity(FinancementRequest request, Financements financement) {
        financement.setTypeFinancement(request.getTypeFinancement());
        financement.setConditionPrincipales(request.getConditionPrincipales());
        financement.setMontantMin(request.getMontantMin());
        financement.setMontantMax(request.getMontantMax());
        financement.setDisponible(request.getDisponible());
    }

    public FinancementResponse toResponse(Financements financement) {
        return FinancementResponse.builder()
                .idFinancement(financement.getIdFinancement())
                .typeFinancement(financement.getTypeFinancement())
                .conditionPrincipales(financement.getConditionPrincipales())
                .montantMin(financement.getMontantMin())
                .montantMax(financement.getMontantMax())
                .disponible(financement.getDisponible())
                .idPartenaire(financement.getPartenaire() != null
                        ? financement.getPartenaire().getIdPartenaire() : null)
                .nomPartenaire(financement.getPartenaire() != null
                        ? financement.getPartenaire().getNomPartenaire() : null)
                .idAdministrateur(financement.getAdministrateur() != null
                        ? financement.getAdministrateur().getId() : null)
                .build();
    }
}