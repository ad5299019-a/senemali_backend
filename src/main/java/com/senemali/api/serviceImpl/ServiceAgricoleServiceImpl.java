package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.ServiceAgricoleRequest;
import com.senemali.api.dto.response.ServiceAgricoleResponse;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.Prestataires;
import com.senemali.api.entity.ServiceAgricoles;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.ServiceAgricoleMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.PrestatireRepository;
import com.senemali.api.repository.ServiceAgricoleRepository;
import com.senemali.api.service.ServiceAgricoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceAgricoleServiceImpl implements ServiceAgricoleService {
    private final ServiceAgricoleRepository serviceRepository;
    private final PrestatireRepository prestatireRepository;
    private final AdministrateurRepository administrateurRepository;
    private final ServiceAgricoleMapper mapper;

    @Override
    @Transactional
    public ServiceAgricoleResponse create(ServiceAgricoleRequest request) {
        Prestataires prestataire = prestatireRepository.findById(request.getIdPrestataire())
                .orElseThrow(() -> new ResourceNotFoundException("Prestataire non trouvé"));
        Administrateurs admin = administrateurRepository.findById(request.getIdAdministrateur())
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur non trouvé"));
        ServiceAgricoles service = mapper.toEntity(request);
        service.setPrestataire(prestataire);
        service.setAdministrateur(admin);
        return mapper.toResponse(serviceRepository.save(service));
    }

    @Override
    public List<ServiceAgricoleResponse> findAll() {
        return serviceRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ServiceAgricoleResponse findById(Long id) {
        return mapper.toResponse(serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service non trouvé")));
    }

    @Override
    @Transactional
    public ServiceAgricoleResponse update(Long id, ServiceAgricoleRequest request) {
        ServiceAgricoles s = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service non trouvé"));
        mapper.updateEntity(request, s);
        return mapper.toResponse(serviceRepository.save(s));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!serviceRepository.existsById(id)) throw new ResourceNotFoundException("Service non trouvé");
        serviceRepository.deleteById(id);
    }

    @Override
    public List<ServiceAgricoleResponse> findByPrestataire(Long idPrestataire) {
        return serviceRepository.findByPrestataire_IdPrestataire(idPrestataire).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ServiceAgricoleResponse> findDisponibles() {
        return serviceRepository.findByDisponible(true).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
