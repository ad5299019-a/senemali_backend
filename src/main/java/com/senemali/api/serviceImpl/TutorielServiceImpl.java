package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.TutorielRequest;
import com.senemali.api.dto.response.TutorielResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.CategorieCultures;
import com.senemali.api.entity.Tutoriels;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.TutorielMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.CategorieCultureRepository;
import com.senemali.api.repository.TutorielRepository;
import com.senemali.api.service.TutorielService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TutorielServiceImpl implements TutorielService {
    private final TutorielRepository tutorielRepository;
    private final CategorieCultureRepository categorieCultureRepository;
    private final AdministrateurRepository administrateurRepository;
    private final TutorielMapper mapper;

    @Override
    @Transactional
    public TutorielResponse create(TutorielRequest request) {
        CategorieCultures categorie = categorieCultureRepository.findById(request.getIdCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée"));
        Administrateurs admin = administrateurRepository.findById(request.getIdAdministrateur())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        Tutoriels tutoriel = mapper.toEntity(request);
        tutoriel.setCategorie(categorie);
        tutoriel.setAdministrateur(admin);
        return mapper.toResponse(tutorielRepository.save(tutoriel));
    }

    @Override
    public List<TutorielResponse> findAll() {
        return tutorielRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public TutorielResponse findById(Long id) {
        return mapper.toResponse(tutorielRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutoriel non trouvé")));
    }

    @Override
    @Transactional
    public TutorielResponse update(Long id, TutorielRequest request) {
        Tutoriels tutoriel = tutorielRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutoriel non trouvé"));
        mapper.updateEntity(request, tutoriel);
        return mapper.toResponse(tutorielRepository.save(tutoriel));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!tutorielRepository.existsById(id)) throw new ResourceNotFoundException("Tutoriel non trouvé");
        tutorielRepository.deleteById(id);
    }

    @Override
    public List<TutorielResponse> findByCategorie(Long idCategorie) {
        return tutorielRepository.findByCategorie_IdCategorie(idCategorie).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
