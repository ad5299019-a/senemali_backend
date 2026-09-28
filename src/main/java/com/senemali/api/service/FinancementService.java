package com.senemali.api.service;

import com.senemali.api.dto.request.FinancementRequest;
import com.senemali.api.dto.response.FinancementResponse;
import java.util.List;

public interface FinancementService {
    FinancementResponse create(FinancementRequest request);
    List<FinancementResponse> findAll();
    FinancementResponse findById(Long id);
    FinancementResponse update(Long id, FinancementRequest request);
    void delete(Long id);
    List<FinancementResponse> findByPartenaire(Long idPartenaire);
    List<FinancementResponse> findDisponibles();
}
