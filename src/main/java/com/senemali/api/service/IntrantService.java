package com.senemali.api.service;

import com.senemali.api.dto.request.IntrantRequest;
import com.senemali.api.dto.response.IntrantResponse;
import com.senemali.api.enums.StatutValidationFournisseur;
import java.util.List;

public interface IntrantService {
    IntrantResponse create(IntrantRequest request);
    List<IntrantResponse> findAll();
    IntrantResponse findById(Long id);
    IntrantResponse update(Long id, IntrantRequest request);
    void delete(Long id);
    List<IntrantResponse> findByFournisseur(Long idFournisseur);
    List<IntrantResponse> findDisponibles();
    IntrantResponse validerIntrant(Long id, Long idAdmin, StatutValidationFournisseur statut);
}
