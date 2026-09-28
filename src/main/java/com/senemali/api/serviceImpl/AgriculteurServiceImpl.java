package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.AgriculteurRequest;
import com.senemali.api.dto.response.AgriculteurResponse;
import com.senemali.api.entity.Agriculteurs;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.AgriculteurMapper;
import com.senemali.api.repository.AgriculteurRepository;
import com.senemali.api.service.AgriculteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgriculteurServiceImpl implements AgriculteurService {
    private final AgriculteurRepository repository;
    private final AgriculteurMapper mapper;

    @Override
    public List<AgriculteurResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public AgriculteurResponse findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agriculteur non trouvé")));
    }

    @Override
    @Transactional
    public AgriculteurResponse update(Long id, AgriculteurRequest request) {
        Agriculteurs a = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agriculteur non trouvé"));
        mapper.updateEntity(request, a);
        return mapper.toResponse(repository.save(a));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Agriculteur non trouvé");
        repository.deleteById(id);
    }
}
