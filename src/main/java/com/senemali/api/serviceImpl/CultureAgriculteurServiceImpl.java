package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.CultureAgriculteurRequest;
import com.senemali.api.dto.response.CultureAgriculteurResponse;
import com.senemali.api.entity.Agriculteurs;
import com.senemali.api.entity.CultureAgriculteurs;
import com.senemali.api.entity.Cultures;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.CultureAgriculteurMapper;
import com.senemali.api.repository.AgriculteurRepository;
import com.senemali.api.repository.CultureAgriculteurRepository;
import com.senemali.api.repository.CultureRepository;
import com.senemali.api.service.CultureAgriculteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CultureAgriculteurServiceImpl implements CultureAgriculteurService {
    private final CultureAgriculteurRepository repository;
    private final AgriculteurRepository agriculteurRepository;
    private final CultureRepository cultureRepository;
    private final CultureAgriculteurMapper mapper;

    @Override
    @Transactional
    public CultureAgriculteurResponse add(CultureAgriculteurRequest request) {
        if (repository.existsByAgriculteur_IdAgriculteurAndCulture_IdCulture(
                request.getIdAgriculteur(), request.getIdCulture())) {
            throw new RuntimeException("Association déjà existante");
        }
        Agriculteurs agriculteur = agriculteurRepository.findById(request.getIdAgriculteur())
                .orElseThrow(() -> new ResourceNotFoundException("Agriculteur non trouvé"));
        Cultures culture = cultureRepository.findById(request.getIdCulture())
                .orElseThrow(() -> new ResourceNotFoundException("Culture non trouvée"));
        CultureAgriculteurs ca = mapper.toEntity(agriculteur, culture);
        return mapper.toResponse(repository.save(ca));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Association non trouvée");
        repository.deleteById(id);
    }

    @Override
    public List<CultureAgriculteurResponse> findByAgriculteur(Long idAgriculteur) {
        return repository.findByAgriculteur_IdAgriculteur(idAgriculteur).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<CultureAgriculteurResponse> findByCulture(Long idCulture) {
        return repository.findByCulture_IdCulture(idCulture).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
