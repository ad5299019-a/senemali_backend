package com.senemali.api.service;

import com.senemali.api.dto.request.ConseilRequest;
import com.senemali.api.dto.response.ConseilResponse;
import java.util.List;

public interface ConseilService {
    ConseilResponse create(ConseilRequest request);
    List<ConseilResponse> findAll();
    ConseilResponse findById(Long id);
    ConseilResponse update(Long id, ConseilRequest request);
    void delete(Long id);
    List<ConseilResponse> findByCulture(Long idCulture);
}
