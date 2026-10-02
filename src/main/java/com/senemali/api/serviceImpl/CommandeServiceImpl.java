package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.CommandeRequest;
import com.senemali.api.dto.request.DetailCommandeRequest;
import com.senemali.api.dto.response.CommandeResponse;
import com.senemali.api.entity.*;
import com.senemali.api.enums.RoleEnum;
import com.senemali.api.enums.StatutCommandeEnum;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.CommandeMapper;
import com.senemali.api.mapper.DetailCommandeMapper;
import com.senemali.api.repository.*;
import com.senemali.api.security.AccessControl;
import com.senemali.api.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository commandeRepository;
    private final ProduitAgricoleRepository produitRepository;
    private final IntrantRepository intrantRepository;
    private final DetailCommandeRepository detailCommandeRepository;
    private final CommandeMapper mapper;
    private final DetailCommandeMapper detailMapper;
    private final AccessControl accessControl;

    @Override
    @Transactional
    public CommandeResponse create(CommandeRequest request) {
        Utilisateurs utilisateur = accessControl.requireCurrentUser();
        boolean acheteur = utilisateur.getRole() == RoleEnum.ACHETEUR;
        boolean agriculteur = utilisateur.getRole() == RoleEnum.AGRICULTEUR;
        if (!acheteur && !agriculteur) {
            throw new AccessDeniedException("Seuls un acheteur ou un agriculteur peuvent passer une commande");
        }
        Commandes commande = Commandes.builder()
                .dateCommande(LocalDateTime.now())
                .status(StatutCommandeEnum.EN_COURS)
                .montantTotal(0)
                .utilisateur(utilisateur)
                .build();
        commande = commandeRepository.save(commande);
        List<DetailCommandes> details = new ArrayList<>();
        int montantTotal = 0;
        Long vendeurId = null;
        for (DetailCommandeRequest dr : request.getDetails()) {
            if ((dr.getIdProduit() == null) == (dr.getIdIntrant() == null)) {
                throw new IllegalArgumentException("Chaque ligne doit cibler un produit ou un intrant, mais pas les deux");
            }
            if (acheteur != (dr.getIdProduit() != null)) {
                throw new IllegalArgumentException(acheteur
                        ? "Un acheteur peut commander uniquement des produits agricoles"
                        : "Un agriculteur peut commander uniquement des intrants");
            }
            DetailCommandes detail = detailMapper.toEntity(dr);
            detail.setCommande(commande);
            int prixUnitaire;
            if (dr.getIdProduit() != null) {
                ProduitAgricoles produit = produitRepository.findById(dr.getIdProduit())
                        .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));
                if (!Boolean.TRUE.equals(produit.getDisponible())) {
                    throw new IllegalArgumentException("Le produit n'est pas disponible");
                }
                Long currentSellerId = produit.getAgriculteur().getId();
                if (vendeurId != null && !vendeurId.equals(currentSellerId)) {
                    throw new IllegalArgumentException("Une commande doit concerner un seul vendeur");
                }
                vendeurId = currentSellerId;
                prixUnitaire = produit.getPrix();
                detail.setProduit(produit);
            } else {
                Intrants intrant = intrantRepository.findById(dr.getIdIntrant())
                        .orElseThrow(() -> new ResourceNotFoundException("Intrant non trouvé"));
                if (!Boolean.TRUE.equals(intrant.getDisponible())) {
                    throw new IllegalArgumentException("L'intrant n'est pas disponible");
                }
                if (intrant.getStatutIntrant() != com.senemali.api.enums.StatutValidationFournisseur.VALIDE
                        || !Boolean.TRUE.equals(intrant.getFournisseur().getStatut())) {
                    throw new IllegalArgumentException("L'intrant ou son fournisseur n'est pas validé");
                }
                Long currentSellerId = intrant.getFournisseur().getId();
                if (vendeurId != null && !vendeurId.equals(currentSellerId)) {
                    throw new IllegalArgumentException("Une commande doit concerner un seul vendeur");
                }
                vendeurId = currentSellerId;
                prixUnitaire = intrant.getPrix();
                detail.setIntrant(intrant);
            }
            detail.setPrixUnitaire(prixUnitaire);
            detail.setMontant(Math.multiplyExact(dr.getQuantite(), prixUnitaire));
            details.add(detail);
            montantTotal = Math.addExact(montantTotal, detail.getMontant());
        }
        detailCommandeRepository.saveAll(details);
        commande.setMontantTotal(montantTotal);
        commande.setDetailCommandes(details);
        return mapper.toResponse(commandeRepository.save(commande));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findAll() {
        return commandeRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommandeResponse findById(Long id) {
        return mapper.toResponse(commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findMine() {
        Utilisateurs utilisateur = accessControl.requireCurrentUser();
        Map<Long, Commandes> commandes = new LinkedHashMap<>();
        if (utilisateur.getRole() == RoleEnum.ACHETEUR || utilisateur.getRole() == RoleEnum.AGRICULTEUR) {
            commandeRepository.findByUtilisateur_Id(utilisateur.getId())
                    .forEach(commande -> commandes.put(commande.getIdCommande(), commande));
        }
        if (utilisateur.getRole() == RoleEnum.AGRICULTEUR) {
            commandeRepository.findByAgriculteurVendeurId(utilisateur.getId())
                    .forEach(commande -> commandes.put(commande.getIdCommande(), commande));
        } else if (utilisateur.getRole() == RoleEnum.FOURNISSEUR) {
            commandeRepository.findByFournisseurVendeurId(utilisateur.getId())
                    .forEach(commande -> commandes.put(commande.getIdCommande(), commande));
        }
        return commandes.values().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByUtilisateur(Long idUtilisateur) {
        return commandeRepository.findByUtilisateur_Id(idUtilisateur).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommandeResponse updateStatut(Long id, StatutCommandeEnum statut) {
        Commandes commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée"));
        Utilisateurs utilisateurCourant = accessControl.requireCurrentUser();
        boolean client = commande.getUtilisateur().getId().equals(utilisateurCourant.getId());
        boolean vendeur = commande.getDetailCommandes().stream().anyMatch(detail ->
                (detail.getProduit() != null
                        && detail.getProduit().getAgriculteur().getId().equals(utilisateurCourant.getId()))
                        || (detail.getIntrant() != null
                        && detail.getIntrant().getFournisseur().getId().equals(utilisateurCourant.getId())));

        if (commande.getStatus() != StatutCommandeEnum.EN_COURS) {
            throw new IllegalArgumentException("Seule une commande en cours peut changer de statut");
        }
        if (statut == StatutCommandeEnum.ANNULER && !client) {
            throw new AccessDeniedException("Seul le client ayant passé la commande peut l'annuler");
        }
        if (statut == StatutCommandeEnum.LIVREE && !vendeur) {
            throw new AccessDeniedException("Seul le vendeur concerné peut marquer la commande comme livrée");
        }
        if (statut == StatutCommandeEnum.EN_COURS) {
            throw new IllegalArgumentException("Le statut EN_COURS est le statut initial de la commande");
        }
        commande.setStatus(statut);
        return mapper.toResponse(commandeRepository.save(commande));
    }
}
