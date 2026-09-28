package com.senemali.api.service;

import com.senemali.api.dto.request.CultureAgriculteurRequest;
import com.senemali.api.dto.response.CultureAgriculteurResponse;
import java.util.List;

public interface CultureAgriculteurService {
    CultureAgriculteurResponse add(CultureAgriculteurRequest request);
    void remove(Long id);
    List<CultureAgriculteurResponse> findByAgriculteur(Long idAgriculteur);
    List<CultureAgriculteurResponse> findByCulture(Long idCulture);
}
