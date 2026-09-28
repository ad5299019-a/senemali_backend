package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.PrixMarcheRequest;
import com.senemali.api.dto.response.PrixMarcheResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.PrixMarche;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.PrixMarcheMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.PrixMarcheRepository;
import com.senemali.api.service.PrixMarcheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrixMarcheServiceImpl implements PrixMarcheService {
    private final PrixMarcheRepository prixMarcheRepository;
    private final AdministrateurRepository administrateurRepository;
    private final PrixMarcheMapper mapper;

    @Override
    @Transactional
    public PrixMarcheResponse create(PrixMarcheRequest request) {
        Administrateurs admin = administrateurRepository.findById(request.getIdAdministrateur())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        PrixMarche prix = mapper.toEntity(request);
        prix.setDateObserv(LocalDateTime.now());
        prix.setAdministrateur(admin);
        return mapper.toResponse(prixMarcheRepository.save(prix));
    }

    @Override
    public List<PrixMarcheResponse> findAll() {
        return prixMarcheRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PrixMarcheResponse findById(Long id) {
        return mapper.toResponse(prixMarcheRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prix non trouvé")));
    }

    @Override
    @Transactional
    public PrixMarcheResponse update(Long id, PrixMarcheRequest request) {
        PrixMarche p = prixMarcheRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prix non trouvé"));
        mapper.updateEntity(request, p);
        return mapper.toResponse(prixMarcheRepository.save(p));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!prixMarcheRepository.existsById(id)) throw new ResourceNotFoundException("Prix non trouvé");
        prixMarcheRepository.deleteById(id);
    }

    @Override
    public List<PrixMarcheResponse> findByNomProduit(String nomProduit) {
        return prixMarcheRepository.findByNomProduitContainingIgnoreCase(nomProduit).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<PrixMarcheResponse> findByLocalisation(String localisation) {
        return prixMarcheRepository.findByLocalisation(localisation).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
