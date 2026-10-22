package com.senemali.api.controller;

import com.senemali.api.dto.request.CommandeRequest;
import com.senemali.api.dto.response.CommandeResponse;
import com.senemali.api.enums.StatutCommandeEnum;
import com.senemali.api.service.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
@Tag(name = "Commandes", description = "Gestion des commandes")
@SecurityRequirement(name = "bearerAuth")
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ACHETEUR', 'AGRICULTEUR')")
    @Operation(summary = "Passer une commande", description = "Un acheteur commande des produits agricoles ; un agriculteur commande des intrants. Une commande ne peut concerner qu'un seul vendeur.")
    public ResponseEntity<CommandeResponse> create(@Valid @RequestBody CommandeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandeService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister toutes les commandes")
    public ResponseEntity<List<CommandeResponse>> findAll() {
        return ResponseEntity.ok(commandeService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCommandeParticipant(#id)")
    @Operation(summary = "Trouver une commande par ID")
    public ResponseEntity<CommandeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.findById(id));
    }

    @GetMapping("/mes-commandes")
    @PreAuthorize("hasAnyRole('ACHETEUR', 'AGRICULTEUR', 'FOURNISSEUR')")
    @Operation(summary = "Lister mes commandes", description = "Retourne les commandes passées par l'utilisateur et, pour les vendeurs, celles concernant leurs produits ou intrants.")
    public ResponseEntity<List<CommandeResponse>> findMine() {
        return ResponseEntity.ok(commandeService.findMine());
    }

    @GetMapping("/mes-commandes/count")
    @PreAuthorize("hasAnyRole('AGRICULTEUR', 'FOURNISSEUR')")
    @Operation(summary = "Compter mes commandes reçues", description = "Compte les commandes distinctes passées auprès de l'agriculteur ou du fournisseur connecté.")
    public ResponseEntity<Long> countMySales() {
        return ResponseEntity.ok(commandeService.countMySales());
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#idUtilisateur)")
    @Operation(summary = "Lister les commandes d'un utilisateur")
    public ResponseEntity<List<CommandeResponse>> findByUtilisateur(@PathVariable Long idUtilisateur) {
        return ResponseEntity.ok(commandeService.findByUtilisateur(idUtilisateur));
    }

    @PatchMapping("/{id}/confirmer")
    @PreAuthorize("@accessControl.isCommandeParticipant(#id)")
    @Operation(summary = "Confirmer une commande", description = "Seul le vendeur concerné peut confirmer une commande en cours.")
    public ResponseEntity<CommandeResponse> confirmer(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.confirmer(id));
    }

    @PatchMapping("/{id}/refuser")
    @PreAuthorize("@accessControl.isCommandeParticipant(#id)")
    @Operation(summary = "Refuser une commande", description = "Seul le vendeur concerné peut refuser une commande en cours.")
    public ResponseEntity<CommandeResponse> refuser(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.refuser(id));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("@accessControl.isCommandeParticipant(#id)")
    @Operation(summary = "Changer le statut d'une commande", description = "Le client peut annuler une commande en cours ; seul le vendeur peut marquer une commande confirmée comme livrée.")
    public ResponseEntity<CommandeResponse> updateStatut(@PathVariable Long id, @RequestParam StatutCommandeEnum statut) {
        return ResponseEntity.ok(commandeService.updateStatut(id, statut));
    }
}
