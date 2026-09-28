package com.senemali.api.service;

import com.senemali.api.dto.request.ProduitAgricoleRequest;
import com.senemali.api.dto.response.ProduitAgricoleResponse;
import java.util.List;

public interface ProduitAgricoleService {
    ProduitAgricoleResponse create(ProduitAgricoleRequest request);
    List<ProduitAgricoleResponse> findAll();
    ProduitAgricoleResponse findById(Long id);
    ProduitAgricoleResponse update(Long id, ProduitAgricoleRequest request);
    void delete(Long id);
    List<ProduitAgricoleResponse> findByAgriculteur(Long idAgriculteur);
    List<ProduitAgricoleResponse> findDisponibles();
    List<ProduitAgricoleResponse> search(String nom);
}
