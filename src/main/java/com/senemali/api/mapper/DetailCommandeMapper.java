package com.senemali.api.mapper;

import com.senemali.api.dto.request.DetailCommandeRequest;
import com.senemali.api.dto.response.DetailCommandeResponse;
import com.senemali.api.entity.DetailCommandes;
import org.springframework.stereotype.Component;

@Component
public class DetailCommandeMapper {
    public DetailCommandes toEntity(DetailCommandeRequest request) {
        DetailCommandes detail = new DetailCommandes();
        detail.setQuantite(request.getQuantite());
        return detail;
    }

    public DetailCommandeResponse toResponse(DetailCommandes detail) {
        return DetailCommandeResponse.builder()
                .idDetail(detail.getIdDetail())
                .quantite(detail.getQuantite())
                .prixUnitaire(detail.getPrixUnitaire())
                .montant(detail.getMontant())
                .idProduit(detail.getProduit() != null ? detail.getProduit().getIdProduit() : null)
                .nomProduit(detail.getProduit() != null ? detail.getProduit().getNomProduit() : null)
                .idIntrant(detail.getIntrant() != null ? detail.getIntrant().getIdIntrant() : null)
                .nomIntrant(detail.getIntrant() != null ? detail.getIntrant().getNomIntrant() : null)
                .build();
    }
}