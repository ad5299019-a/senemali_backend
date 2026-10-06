package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.CategorieCultureRequest;
import com.senemali.api.dto.response.CategorieCultureResponse;
import com.senemali.api.dto.response.CultureResponse;
import com.senemali.api.entity.CategorieCultures;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.CategorieCultureMapper;
import com.senemali.api.repository.CategorieCultureRepository;
import com.senemali.api.service.CategorieCultureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieCultureServiceImpl implements CategorieCultureService {
    private final CategorieCultureRepository repository;
    private final CategorieCultureMapper mapper;

    @Override
    @Transactional
    public CategorieCultureResponse create(CategorieCultureRequest request) {
        CategorieCultures cat = mapper.toEntity(request);
        return mapper.toResponse(repository.save(cat));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieCultureResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategorieCultureResponse findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée")));
    }

    @Override
    @Transactional
    public CategorieCultureResponse update(Long id, CategorieCultureRequest request) {
        CategorieCultures cat = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée"));
        mapper.updateEntity(request, cat);
        return mapper.toResponse(repository.save(cat));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Catégorie non trouvée");
        repository.deleteById(id);
    }
}
