package com.senemali.api.service;

import com.senemali.api.dto.request.CultureRequest;
import com.senemali.api.dto.response.CultureResponse;
import java.util.List;

public interface CultureService {
    CultureResponse create(CultureRequest request);
    List<CultureResponse> findAll();
    CultureResponse findById(Long id);
    CultureResponse update(Long id, CultureRequest request);
    void delete(Long id);
    List<CultureResponse> findByCategorie(Long idCategorie);
}
