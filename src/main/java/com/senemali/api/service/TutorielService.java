package com.senemali.api.service;

import com.senemali.api.dto.request.TutorielRequest;
import com.senemali.api.dto.response.TutorielResponse;
import java.util.List;

public interface TutorielService {
    TutorielResponse create(TutorielRequest request);
    List<TutorielResponse> findAll();
    TutorielResponse findById(Long id);
    TutorielResponse update(Long id, TutorielRequest request);
    void delete(Long id);
    List<TutorielResponse> findByCategorie(Long idCategorie);
}
