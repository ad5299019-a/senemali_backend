package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.CommandeRequest;
import com.senemali.api.dto.request.DetailCommandeRequest;
import com.senemali.api.dto.response.CommandeResponse;
import com.senemali.api.dto.response.DetailCommandeResponse;
import com.senemali.api.entity.*;
import com.senemali.api.enums.StatutCommandeEnum;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.CommandeMapper;
import com.senemali.api.mapper.DetailCommandeMapper;
import com.senemali.api.repository.*;
import com.senemali.api.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository commandeRepository;
    private final UtilisateursRepository utilisateursRepository;
    private final ProduitAgricoleRepository produitRepository;
    private final IntrantRepository intrantRepository;
    private final DetailCommandeRepository detailCommandeRepository;
    private final CommandeMapper mapper;
    private final DetailCommandeMapper detailMapper;

    @Override
    @Transactional
    public CommandeResponse create(CommandeRequest request) {
        Utilisateurs utilisateur = utilisateursRepository.findById(request.getIdUtilisateur())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        Commandes commande = Commandes.builder()
                .dateCommande(LocalDateTime.now())
                .status(StatutCommandeEnum.EN_COURS)
                .montantTotal(0)
                .utilisateur(utilisateur)
                .build();
        commande = commandeRepository.save(commande);
        List<DetailCommandes> details = new ArrayList<>();
        int montantTotal = 0;
        for (DetailCommandeRequest dr : request.getDetails()) {
            DetailCommandes detail = detailMapper.toEntity(dr);
            detail.setMontant(dr.getQuantite() * dr.getPrixUnitaire());
            detail.setCommande(commande);
            if (dr.getIdProduit() != null) {
                ProduitAgricoles produit = produitRepository.findById(dr.getIdProduit())
                        .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));
                detail.setProduit(produit);
            }
            if (dr.getIdIntrant() != null) {
                Intrants intrant = intrantRepository.findById(dr.getIdIntrant())
                        .orElseThrow(() -> new ResourceNotFoundException("Intrant non trouvé"));
                detail.setIntrant(intrant);
            }
            details.add(detail);
            montantTotal += detail.getMontant();
        }
        detailCommandeRepository.saveAll(details);
        commande.setMontantTotal(montantTotal);
        commande.setDetailCommandes(details);
        return toResponse(commandeRepository.save(commande));
    }

    @Override
    public List<CommandeResponse> findAll() {
        return commandeRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CommandeResponse findById(Long id) {
        return mapper.toResponse(commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée")));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!commandeRepository.existsById(id)) throw new ResourceNotFoundException("Commande non trouvée");
        commandeRepository.deleteById(id);
    }

    @Override
    public List<CommandeResponse> findByUtilisateur(Long idUtilisateur) {
        return commandeRepository.findByUtilisateur_Id(idUtilisateur).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommandeResponse updateStatut(Long id, StatutCommandeEnum statut) {
        Commandes commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée"));
        commande.setStatus(statut);
        return mapper.toResponse(commandeRepository.save(commande));
    }
}
