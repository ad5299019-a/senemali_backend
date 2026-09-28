package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.CultureRequest;
import com.senemali.api.dto.response.CultureResponse;
import com.senemali.api.entity.CategorieCultures;
import com.senemali.api.entity.Cultures;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.CultureMapper;
import com.senemali.api.repository.CategorieCultureRepository;
import com.senemali.api.repository.CultureRepository;
import com.senemali.api.service.CultureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CultureServiceImpl implements CultureService {
    private final CultureRepository cultureRepository;
    private final CategorieCultureRepository categorieCultureRepository;
    private final CultureMapper mapper;

    @Override
    @Transactional
    public CultureResponse create(CultureRequest request) {
        CategorieCultures categorie = categorieCultureRepository.findById(request.getIdCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée"));
        Cultures culture = mapper.toEntity(request);
        culture.setCategorie(categorie);
        return mapper.toResponse(cultureRepository.save(culture));
    }

    @Override
    public List<CultureResponse> findAll() {
        return cultureRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CultureResponse findById(Long id) {
        return mapper.toResponse(cultureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Culture non trouvée")));
    }

    @Override
    @Transactional
    public CultureResponse update(Long id, CultureRequest request) {
        Cultures culture = cultureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Culture non trouvée"));
        CategorieCultures categorie = categorieCultureRepository.findById(request.getIdCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée"));
        mapper.updateEntity(request, culture);
        culture.setCategorie(categorie);
        return mapper.toResponse(cultureRepository.save(culture));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!cultureRepository.existsById(id)) throw new ResourceNotFoundException("Culture non trouvée");
        cultureRepository.deleteById(id);
    }

    @Override
    public List<CultureResponse> findByCategorie(Long idCategorie) {
        return cultureRepository.findByCategorie_IdCategorie(idCategorie).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
