package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.PrestatireRequest;
import com.senemali.api.dto.response.PrestatireResponse;
import com.senemali.api.dto.response.ServiceAgricoleResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.Prestataires;
import com.senemali.api.enums.TypePrestataireEnum;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.PrestatireMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.PrestatireRepository;
import com.senemali.api.service.PrestatireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrestatireServiceImpl implements PrestatireService {
    private final PrestatireRepository prestatireRepository;
    private final AdministrateurRepository administrateurRepository;
    private final PrestatireMapper mapper;

    @Override
    @Transactional
    public PrestatireResponse create(PrestatireRequest request) {
        Administrateurs admin = administrateurRepository.findById(request.getIdAdministrateur())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        Prestataires prestataire = mapper.toEntity(request);
        prestataire.setAdministrateur(admin);
        return mapper.toResponse(prestatireRepository.save(prestataire));
    }

    @Override
    public List<PrestatireResponse> findAll() {
        return prestatireRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PrestatireResponse findById(Long id) {
        return mapper.toResponse(prestatireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestataire non trouvé")));
    }

    @Override
    @Transactional
    public PrestatireResponse update(Long id, PrestatireRequest request) {
        Prestataires p = prestatireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestataire non trouvé"));
        mapper.updateEntity(request, p);
        return mapper.toResponse(prestatireRepository.save(p));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!prestatireRepository.existsById(id)) throw new ResourceNotFoundException("Prestataire non trouvé");
        prestatireRepository.deleteById(id);
    }

    @Override
    public List<PrestatireResponse> findByType(TypePrestataireEnum type) {
        return prestatireRepository.findByTypePrestataire(type).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
