package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.IntrantRequest;
import com.senemali.api.dto.response.IntrantResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.Fournisseurs;
import com.senemali.api.entity.Intrants;
import com.senemali.api.enums.StatutValidationFournisseur;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.IntrantMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.FournisseurRepository;
import com.senemali.api.repository.IntrantRepository;
import com.senemali.api.service.IntrantService;
import com.senemali.api.security.AccessControl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IntrantServiceImpl implements IntrantService {
    private final IntrantRepository intrantRepository;
    private final FournisseurRepository fournisseurRepository;
    private final AdministrateurRepository administrateurRepository;
    private final IntrantMapper mapper;
    private final AccessControl accessControl;

    @Override
    @Transactional
    public IntrantResponse create(IntrantRequest request) {
        Fournisseurs fournisseur = fournisseurRepository.findById(request.getIdFournisseur())
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé"));
        Intrants intrant = mapper.toEntity(request);
        intrant.setFournisseur(fournisseur);
        intrant.setStatutIntrant(StatutValidationFournisseur.EN_ATTENTE);
        return mapper.toResponse(intrantRepository.save(intrant));
    }

    @Override
    public List<IntrantResponse> findAll() {
        return intrantRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public IntrantResponse findById(Long id) {
        return mapper.toResponse(intrantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intrant non trouvé")));
    }

    @Override
    @Transactional
    public IntrantResponse update(Long id, IntrantRequest request) {
        Intrants intrant = intrantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intrant non trouvé"));
        mapper.updateEntity(request, intrant);
        return mapper.toResponse(intrantRepository.save(intrant));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!intrantRepository.existsById(id)) throw new ResourceNotFoundException("Intrant non trouvé");
        intrantRepository.deleteById(id);
    }

    @Override
    public List<IntrantResponse> findByFournisseur(Long idFournisseur) {
        return intrantRepository.findByFournisseur_Id(idFournisseur).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<IntrantResponse> findDisponibles() {
        return intrantRepository.findByDisponible(true).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IntrantResponse validerIntrant(Long id, StatutValidationFournisseur statut) {
        Intrants intrant = intrantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intrant non trouvé"));
        Administrateurs admin = administrateurRepository.findById(accessControl.currentAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        intrant.setStatutIntrant(statut);
        intrant.setAdministrateurValidation(admin);
        return mapper.toResponse(intrantRepository.save(intrant));
    }
}
