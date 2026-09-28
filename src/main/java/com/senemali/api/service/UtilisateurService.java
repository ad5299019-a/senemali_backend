package com.senemali.api.service;

import com.senemali.api.dto.request.UtilisateurRequest;
import com.senemali.api.dto.response.UtilisateurResponse;
import java.util.List;

public interface UtilisateurService {
    List<UtilisateurResponse> findAll();
    UtilisateurResponse findById(Long id);
    UtilisateurResponse update(Long id, UtilisateurRequest request);
    void delete(Long id);
    UtilisateurResponse toggleStatut(Long id);
}
