package com.senemali.api.service;

import com.senemali.api.dto.request.PartenaireFinancierRequest;
import com.senemali.api.dto.response.PartenaireFinancierResponse;
import com.senemali.api.enums.TypePartenaireEnum;
import java.util.List;

public interface PartenaireFinancierService {
    PartenaireFinancierResponse create(PartenaireFinancierRequest request);
    List<PartenaireFinancierResponse> findAll();
    PartenaireFinancierResponse findById(Long id);
    PartenaireFinancierResponse update(Long id, PartenaireFinancierRequest request);
    void delete(Long id);
    List<PartenaireFinancierResponse> findByType(TypePartenaireEnum type);
}
