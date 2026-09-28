package com.senemali.api.mapper;

import com.senemali.api.dto.request.ServiceAgricoleRequest;
import com.senemali.api.dto.response.ServiceAgricoleResponse;
import com.senemali.api.entity.ServiceAgricoles;
import org.springframework.stereotype.Component;

@Component
public class ServiceAgricoleMapper {
    public ServiceAgricoles toEntity(ServiceAgricoleRequest request) {
        ServiceAgricoles service = new ServiceAgricoles();
        updateEntity(request, service);
        return service;
    }

    public void updateEntity(ServiceAgricoleRequest request, ServiceAgricoles service) {
        service.setTypeService(request.getTypeService());
        service.setDescription(request.getDescription());
        service.setCoutEstime(request.getCoutEstime());
        service.setImageUrl(request.getImageUrl());
        service.setDisponible(request.getDisponible());
    }

    public ServiceAgricoleResponse toResponse(ServiceAgricoles service) {
        return ServiceAgricoleResponse.builder()
                .idService(service.getIdService())
                .typeService(service.getTypeService())
                .description(service.getDescription())
                .coutEstime(service.getCoutEstime())
                .imageUrl(service.getImageUrl())
                .disponible(service.getDisponible())
                .idPrestataire(service.getPrestataire() != null
                        ? service.getPrestataire().getIdPrestataire() : null)
                .nomPrestataire(service.getPrestataire() != null
                        ? service.getPrestataire().getNomPrestataire() : null)
                .build();
    }
}