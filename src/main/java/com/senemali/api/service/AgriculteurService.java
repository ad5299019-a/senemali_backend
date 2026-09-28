package com.senemali.api.service;

import com.senemali.api.dto.request.AgriculteurRequest;
import com.senemali.api.dto.response.AgriculteurResponse;
import java.util.List;

public interface AgriculteurService {
    List<AgriculteurResponse> findAll();
    AgriculteurResponse findById(Long id);
    AgriculteurResponse update(Long id, AgriculteurRequest request);
    void delete(Long id);
}
