package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.AcheteursRequest;
import com.senemali.api.dto.response.AcheteurResponse;
import com.senemali.api.entity.Acheteurs;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.AcheteurMapper;
import com.senemali.api.repository.AcheteurRepository;
import com.senemali.api.service.AcheteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcheteurServiceImpl implements AcheteurService {

    private final AcheteurRepository repository;
    private final AcheteurMapper mapper;

    @Override
    public List<AcheteurResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public AcheteurResponse findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Acheteur non trouvé avec l'ID: " + id)));
    }

    @Override
    @Transactional
    public AcheteurResponse update(Long id, AcheteursRequest request) {
        Acheteurs acheteur = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Acheteur non trouvé avec l'ID: " + id));
        mapper.updateEntity(request, acheteur);
        return mapper.toResponse(repository.save(acheteur));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Acheteur non trouvé avec l'ID: " + id);
        }
        repository.deleteById(id);
    }

}
