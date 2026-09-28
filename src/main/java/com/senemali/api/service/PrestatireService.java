package com.senemali.api.service;

import com.senemali.api.dto.request.PrestatireRequest;
import com.senemali.api.dto.response.PrestatireResponse;
import com.senemali.api.enums.TypePrestataireEnum;
import java.util.List;

public interface PrestatireService {
    PrestatireResponse create(PrestatireRequest request);
    List<PrestatireResponse> findAll();
    PrestatireResponse findById(Long id);
    PrestatireResponse update(Long id, PrestatireRequest request);
    void delete(Long id);
    List<PrestatireResponse> findByType(TypePrestataireEnum type);
}
