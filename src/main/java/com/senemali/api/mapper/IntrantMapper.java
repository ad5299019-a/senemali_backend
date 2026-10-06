package com.senemali.api.mapper;

import com.senemali.api.dto.request.IntrantRequest;
import com.senemali.api.dto.response.IntrantResponse;
import com.senemali.api.entity.Intrants;
import org.springframework.stereotype.Component;

@Component
public class IntrantMapper {
    public Intrants toEntity(IntrantRequest request) {
        Intrants intrant = new Intrants();
        updateEntity(request, intrant);
        return intrant;
    }

    public void updateEntity(IntrantRequest request, Intrants intrant) {
        intrant.setNomIntrant(request.getNomIntrant());
        intrant.setTypeIntrant(request.getTypeIntrant());
        intrant.setDescription(request.getDescription());
        intrant.setPrix(request.getPrix());
        intrant.setQuantite(request.getQuantite());
        intrant.setDisponible(request.getDisponible());
        intrant.setImageUrl(request.getImageUrl());
    }

    public IntrantResponse toResponse(Intrants intrant) {
        return IntrantResponse.builder()
                .idIntrant(intrant.getIdIntrant())
                .nomIntrant(intrant.getNomIntrant())
                .typeIntrant(intrant.getTypeIntrant())
                .description(intrant.getDescription())
                .prix(intrant.getPrix())
                .quantite(intrant.getQuantite())
                .disponible(intrant.getDisponible())
                .statutIntrant(intrant.getStatutIntrant())
                .imageUrl(intrant.getImageUrl())
                .idFournisseur(intrant.getFournisseur() != null
                        ? intrant.getFournisseur().getId() : null)
                .nomFournisseur(intrant.getFournisseur() != null ? intrant.getFournisseur().getNom() : null)
                .build();
    }
}