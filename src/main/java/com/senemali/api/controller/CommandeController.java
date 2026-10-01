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
    @PreAuthorize("hasRole('ACHETEUR') and @accessControl.isCurrentUser(#request.idUtilisateur)")
    @Operation(summary = "Passer une commande")
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
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCommandeOwner(#id)")
    @Operation(summary = "Trouver une commande par ID")
    public ResponseEntity<CommandeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.findById(id));
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#idUtilisateur)")
    @Operation(summary = "Lister les commandes d'un utilisateur")
    public ResponseEntity<List<CommandeResponse>> findByUtilisateur(@PathVariable Long idUtilisateur) {
        return ResponseEntity.ok(commandeService.findByUtilisateur(idUtilisateur));
    }

    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Changer le statut d'une commande")
    public ResponseEntity<CommandeResponse> updateStatut(@PathVariable Long id, @RequestParam StatutCommandeEnum statut) {
        return ResponseEntity.ok(commandeService.updateStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une commande")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commandeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
