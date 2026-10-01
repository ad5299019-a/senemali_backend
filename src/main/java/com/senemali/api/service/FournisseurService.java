package com.senemali.api.service;

import com.senemali.api.dto.request.FournisseurRequest;
import com.senemali.api.dto.response.FournisseurResponse;
import com.senemali.api.enums.StatutValidationFournisseur;
import java.util.List;

public interface FournisseurService {
    List<FournisseurResponse> findAll();
    FournisseurResponse findById(Long id);
    FournisseurResponse update(Long id, FournisseurRequest request);
    void delete(Long id);
    FournisseurResponse validerFournisseur(Long id, StatutValidationFournisseur statut);
    List<FournisseurResponse> findByStatut(StatutValidationFournisseur statut);
}
