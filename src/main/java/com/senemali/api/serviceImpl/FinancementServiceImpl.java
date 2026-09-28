package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.FinancementRequest;
import com.senemali.api.dto.response.FinancementResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.Financements;
import com.senemali.api.entity.PartenaireFinanciers;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.FinancementMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.FinancementRepository;
import com.senemali.api.repository.PartenaireFinancierRepository;
import com.senemali.api.service.FinancementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancementServiceImpl implements FinancementService {
    private final FinancementRepository financementRepository;
    private final PartenaireFinancierRepository partenaireRepository;
    private final AdministrateurRepository administrateurRepository;
    private final FinancementMapper mapper;

    @Override
    @Transactional
    public FinancementResponse create(FinancementRequest request) {
        PartenaireFinanciers partenaire = partenaireRepository.findById(request.getIdPartenaire())
                .orElseThrow(() -> new ResourceNotFoundException("Partenaire non trouvé"));
        Administrateurs admin = administrateurRepository.findById(request.getIdAdministrateur())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        Financements financement = mapper.toEntity(request);
        financement.setPartenaire(partenaire);
        financement.setAdministrateur(admin);
        return mapper.toResponse(financementRepository.save(financement));
    }

    @Override
    public List<FinancementResponse> findAll() {
        return financementRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public FinancementResponse findById(Long id) {
        return mapper.toResponse(financementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Financement non trouvé")));
    }

    @Override
    @Transactional
    public FinancementResponse update(Long id, FinancementRequest request) {
        Financements financement = financementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Financement non trouvé"));
        mapper.updateEntity(request, financement);
        return mapper.toResponse(financementRepository.save(financement));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!financementRepository.existsById(id)) throw new ResourceNotFoundException("Financement non trouvé");
        financementRepository.deleteById(id);
    }

    @Override
    public List<FinancementResponse> findByPartenaire(Long idPartenaire) {
        return financementRepository.findByPartenaire_IdPartenaire(idPartenaire).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<FinancementResponse> findDisponibles() {
        return financementRepository.findByDisponible(true).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
