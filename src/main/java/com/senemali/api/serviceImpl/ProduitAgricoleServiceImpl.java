package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.ProduitAgricoleRequest;
import com.senemali.api.dto.response.ProduitAgricoleResponse;
import com.senemali.api.entity.Agriculteurs;
import com.senemali.api.entity.Administrateurs;
import com.senemali.api.entity.ProduitAgricoles;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.ProduitAgricoleMapper;
import com.senemali.api.repository.AdministrateurRepository;
import com.senemali.api.repository.AgriculteurRepository;
import com.senemali.api.repository.ProduitAgricoleRepository;
import com.senemali.api.service.ProduitAgricoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProduitAgricoleServiceImpl implements ProduitAgricoleService {
    private final ProduitAgricoleRepository produitRepository;
    private final AgriculteurRepository agriculteurRepository;
    private final AdministrateurRepository administrateurRepository;
    private final ProduitAgricoleMapper mapper;

    @Override
    @Transactional
    public ProduitAgricoleResponse create(ProduitAgricoleRequest request) {
        Agriculteurs agriculteur = agriculteurRepository.findById(request.getIdAgriculteur())
                .orElseThrow(() -> new ResourceNotFoundException("Agriculteur non trouvé"));
        ProduitAgricoles produit = mapper.toEntity(request);
        produit.setDatePub(LocalDateTime.now());
        produit.setAgriculteur(agriculteur);
        return mapper.toResponse(produitRepository.save(produit));
    }

    @Override
    public List<ProduitAgricoleResponse> findAll() {
        return produitRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProduitAgricoleResponse findById(Long id) {
        return mapper.toResponse(produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé")));
    }

    @Override
    @Transactional
    public ProduitAgricoleResponse update(Long id, ProduitAgricoleRequest request) {
        ProduitAgricoles produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));
        mapper.updateEntity(request, produit);
        return mapper.toResponse(produitRepository.save(produit));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!produitRepository.existsById(id)) throw new ResourceNotFoundException("Produit non trouvé");
        produitRepository.deleteById(id);
    }

    @Override
    public List<ProduitAgricoleResponse> findByAgriculteur(Long idAgriculteur) {
        return produitRepository.findByAgriculteur_IdAgriculteur(idAgriculteur).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProduitAgricoleResponse> findDisponibles() {
        return produitRepository.findByDisponible(true).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProduitAgricoleResponse> search(String nom) {
        return produitRepository.findByNomProduitContainingIgnoreCase(nom).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
