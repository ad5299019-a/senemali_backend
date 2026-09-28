package com.senemali.api.service;

import com.senemali.api.dto.request.ServiceAgricoleRequest;
import com.senemali.api.dto.response.ServiceAgricoleResponse;
import java.util.List;

public interface ServiceAgricoleService {
    ServiceAgricoleResponse create(ServiceAgricoleRequest request);
    List<ServiceAgricoleResponse> findAll();
    ServiceAgricoleResponse findById(Long id);
    ServiceAgricoleResponse update(Long id, ServiceAgricoleRequest request);
    void delete(Long id);
    List<ServiceAgricoleResponse> findByPrestataire(Long idPrestataire);
    List<ServiceAgricoleResponse> findDisponibles();
}
