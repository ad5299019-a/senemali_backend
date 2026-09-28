package com.senemali.api.mapper;

import com.senemali.api.dto.request.PrestatireRequest;
import com.senemali.api.dto.response.PrestatireResponse;
import com.senemali.api.dto.response.ServiceAgricoleResponse;
import com.senemali.api.entity.Prestataires;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrestatireMapper {
    public Prestataires toEntity(PrestatireRequest request) {
        Prestataires prestataire = new Prestataires();
        updateEntity(request, prestataire);
        return prestataire;
    }

    public void updateEntity(PrestatireRequest request, Prestataires prestataire) {
        prestataire.setNomPrestataire(request.getNomPrestataire());
        prestataire.setTypePrestataire(request.getTypePrestataire());
        prestataire.setDescription(request.getDescription());
        prestataire.setSpecialite(request.getSpecialite());
        prestataire.setTelephone(request.getTelephone());
        prestataire.setLocalisation(request.getLocalisation());
        prestataire.setImageUrl(request.getImageUrl());
    }

    public PrestatireResponse toResponse(Prestataires prestataire) {
        List<ServiceAgricoleResponse> services = prestataire.getServices() == null ? List.of()
                : prestataire.getServices().stream()
                .map(service -> ServiceAgricoleResponse.builder()
                        .idService(service.getIdService())
                        .typeService(service.getTypeService())
                        .description(service.getDescription())
                        .coutEstime(service.getCoutEstime())
                        .imageUrl(service.getImageUrl())
                        .disponible(service.getDisponible())
                        .idPrestataire(prestataire.getIdPrestataire())
                        .nomPrestataire(prestataire.getNomPrestataire())
                        .build())
                .toList();
        return PrestatireResponse.builder()
                .idPrestataire(prestataire.getIdPrestataire())
                .nomPrestataire(prestataire.getNomPrestataire())
                .typePrestataire(prestataire.getTypePrestataire())
                .description(prestataire.getDescription())
                .specialite(prestataire.getSpecialite())
                .telephone(prestataire.getTelephone())
                .localisation(prestataire.getLocalisation())
                .imageUrl(prestataire.getImageUrl())
                .services(services)
                .build();
    }
}