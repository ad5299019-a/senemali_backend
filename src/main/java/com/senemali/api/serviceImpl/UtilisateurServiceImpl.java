package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.UtilisateurRequest;
import com.senemali.api.dto.response.UtilisateurResponse;
import com.senemali.api.entity.Utilisateurs;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.UtilisateurMapper;
import com.senemali.api.repository.UtilisateursRepository;
import com.senemali.api.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateursRepository repository;
    private final UtilisateurMapper mapper;

    @Override
    public List<UtilisateurResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public UtilisateurResponse findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé")));
    }

    @Override
    @Transactional
    public UtilisateurResponse update(Long id, UtilisateurRequest request) {
        Utilisateurs u = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        mapper.updateEntity(request, u);
        return mapper.toResponse(repository.save(u));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Utilisateur non trouvé");
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public UtilisateurResponse toggleStatut(Long id) {
        Utilisateurs u = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        u.setStatut(!Boolean.TRUE.equals(u.getStatut()));
        return mapper.toResponse(repository.save(u));
    }
}
