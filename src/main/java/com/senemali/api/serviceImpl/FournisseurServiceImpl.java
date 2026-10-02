package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.FournisseurRequest;
import com.senemali.api.dto.response.FournisseurResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.Fournisseurs;
import com.senemali.api.enums.StatutValidationFournisseur;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.FournisseurMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.FournisseurRepository;
import com.senemali.api.service.FournisseurService;
import com.senemali.api.security.AccessControl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {
    private final FournisseurRepository fournisseurRepository;
    private final AdministrateurRepository administrateurRepository;
    private final FournisseurMapper mapper;
    private final AccessControl accessControl;

    @Override
    public List<FournisseurResponse> findAll() {
        return fournisseurRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public FournisseurResponse findById(Long id) {
        return mapper.toResponse(fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé")));
    }

    @Override
    @Transactional
    public FournisseurResponse update(Long id, FournisseurRequest request) {
        Fournisseurs f = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé"));
        mapper.updateEntity(request, f);
        return mapper.toResponse(fournisseurRepository.save(f));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!fournisseurRepository.existsById(id)) throw new ResourceNotFoundException("Fournisseur non trouvé");
        fournisseurRepository.deleteById(id);
    }

    @Override
    @Transactional
    public FournisseurResponse validerFournisseur(Long id, StatutValidationFournisseur statut) {
        Fournisseurs f = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé"));
        Administrateurs admin = administrateurRepository.findById(accessControl.currentAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        f.setStatutFournisseur(statut);
        f.setStatut(statut == StatutValidationFournisseur.VALIDE);
        f.setAdministrateurValidation(admin);
        return mapper.toResponse(fournisseurRepository.save(f));
    }

    @Override
    public List<FournisseurResponse> findByStatut(StatutValidationFournisseur statut) {
        return fournisseurRepository.findByStatutFournisseur(statut).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
