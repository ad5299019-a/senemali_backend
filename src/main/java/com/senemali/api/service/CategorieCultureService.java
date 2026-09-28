package com.senemali.api.service;

import com.senemali.api.dto.request.CategorieCultureRequest;
import com.senemali.api.dto.response.CategorieCultureResponse;
import java.util.List;

public interface CategorieCultureService {
    CategorieCultureResponse create(CategorieCultureRequest request);
    List<CategorieCultureResponse> findAll();
    CategorieCultureResponse findById(Long id);
    CategorieCultureResponse update(Long id, CategorieCultureRequest request);
    void delete(Long id);
}
