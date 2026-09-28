package com.senemali.api.service;

import com.senemali.api.dto.request.AcheteursRequest;
import com.senemali.api.dto.response.AcheteurResponse;

import java.util.List;

/**
 * Service pour la gestion des acheteurs.
 */
public interface AcheteurService {
    List<AcheteurResponse> findAll();
    AcheteurResponse findById(Long id);
    AcheteurResponse update(Long id, AcheteursRequest request);
    void delete(Long id);
}
