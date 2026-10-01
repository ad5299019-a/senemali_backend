package com.senemali.api.mapper;

import com.senemali.api.dto.request.PrixMarcheRequest;
import com.senemali.api.dto.response.PrixMarcheResponse;
import com.senemali.api.entity.PrixMarche;
import org.springframework.stereotype.Component;

@Component
public class PrixMarcheMapper {
    public PrixMarche toEntity(PrixMarcheRequest request) {
        PrixMarche prix = new PrixMarche();
        updateEntity(request, prix);
        return prix;
    }

    public void updateEntity(PrixMarcheRequest request, PrixMarche prix) {
        prix.setNomProduit(request.getNomProduit());
        prix.setPrixProduit(request.getPrixProduit());
        prix.setUnite(request.getUnite());
        prix.setLocalisation(request.getLocalisation());
    }

    public PrixMarcheResponse toResponse(PrixMarche prix) {
        return PrixMarcheResponse.builder()
                .idPrix(prix.getIdPrix())
                .nomProduit(prix.getNomProduit())
                .prixProduit(prix.getPrixProduit())
                .unite(prix.getUnite())
                .localisation(prix.getLocalisation())
                .dateObserv(prix.getDateObserv())
                .idAdministrateur(prix.getAdministrateur() != null
                        ? prix.getAdministrateur().getId() : null)
                .build();
    }
}