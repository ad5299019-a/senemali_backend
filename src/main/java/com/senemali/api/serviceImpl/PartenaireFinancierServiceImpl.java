package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.PartenaireFinancierRequest;
import com.senemali.api.dto.response.FinancementResponse;
import com.senemali.api.dto.response.PartenaireFinancierResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.PartenaireFinanciers;
import com.senemali.api.enums.TypePartenaireEnum;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.PartenaireFinancierMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.PartenaireFinancierRepository;
import com.senemali.api.security.AccessControl;
import com.senemali.api.service.PartenaireFinancierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartenaireFinancierServiceImpl implements PartenaireFinancierService {
    private final PartenaireFinancierRepository partenaireRepository;
    private final AdministrateurRepository administrateurRepository;
    private final PartenaireFinancierMapper mapper;
    private final AccessControl accessControl;

    @Override
    @Transactional
    public PartenaireFinancierResponse create(PartenaireFinancierRequest request) {
        Administrateurs admin = administrateurRepository.findById(accessControl.currentAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        PartenaireFinanciers partenaire = mapper.toEntity(request);
        partenaire.setAdministrateur(admin);
        return mapper.toResponse(partenaireRepository.save(partenaire));
    }

    @Override
    public List<PartenaireFinancierResponse> findAll() {
        return partenaireRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PartenaireFinancierResponse findById(Long id) {
        return mapper.toResponse(partenaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partenaire non trouvé")));
    }

    @Override
    @Transactional
    public PartenaireFinancierResponse update(Long id, PartenaireFinancierRequest request) {
        PartenaireFinanciers p = partenaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partenaire non trouvé"));
        mapper.updateEntity(request, p);
        return mapper.toResponse(partenaireRepository.save(p));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!partenaireRepository.existsById(id)) throw new ResourceNotFoundException("Partenaire non trouvé");
        partenaireRepository.deleteById(id);
    }

    @Override
    public List<PartenaireFinancierResponse> findByType(TypePartenaireEnum type) {
        return partenaireRepository.findByTypepartenaire(type).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
