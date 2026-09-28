package com.senemali.api.service;

import com.senemali.api.dto.request.PrixMarcheRequest;
import com.senemali.api.dto.response.PrixMarcheResponse;
import java.util.List;

public interface PrixMarcheService {
    PrixMarcheResponse create(PrixMarcheRequest request);
    List<PrixMarcheResponse> findAll();
    PrixMarcheResponse findById(Long id);
    PrixMarcheResponse update(Long id, PrixMarcheRequest request);
    void delete(Long id);
    List<PrixMarcheResponse> findByNomProduit(String nomProduit);
    List<PrixMarcheResponse> findByLocalisation(String localisation);
}
