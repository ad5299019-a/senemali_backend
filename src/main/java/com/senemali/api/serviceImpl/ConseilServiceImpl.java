package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.ConseilRequest;
import com.senemali.api.dto.response.ConseilResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.Conseils;
import com.senemali.api.entity.Cultures;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.ConseilMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.ConseilRepository;
import com.senemali.api.repository.CultureRepository;
import com.senemali.api.service.ConseilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConseilServiceImpl implements ConseilService {
    private final ConseilRepository conseilRepository;
    private final CultureRepository cultureRepository;
    private final AdministrateurRepository administrateurRepository;
    private final ConseilMapper mapper;

    @Override
    @Transactional
    public ConseilResponse create(ConseilRequest request) {
        Cultures culture = cultureRepository.findById(request.getIdCulture())
                .orElseThrow(() -> new ResourceNotFoundException("Culture non trouvée"));
        Administrateurs admin = administrateurRepository.findById(request.getIdAdministrateur())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        Conseils conseil = mapper.toEntity(request);
        conseil.setDatePub(LocalDate.now());
        conseil.setCulture(culture);
        conseil.setAdministrateur(admin);
        return mapper.toResponse(conseilRepository.save(conseil));
    }

    @Override
    public List<ConseilResponse> findAll() {
        return conseilRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ConseilResponse findById(Long id) {
        return mapper.toResponse(conseilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conseil non trouvé")));
    }

    @Override
    @Transactional
    public ConseilResponse update(Long id, ConseilRequest request) {
        Conseils conseil = conseilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conseil non trouvé"));
        mapper.updateEntity(request, conseil);
        return mapper.toResponse(conseilRepository.save(conseil));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!conseilRepository.existsById(id)) throw new ResourceNotFoundException("Conseil non trouvé");
        conseilRepository.deleteById(id);
    }

    @Override
    public List<ConseilResponse> findByCulture(Long idCulture) {
        return conseilRepository.findByCulture_IdCulture(idCulture).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
