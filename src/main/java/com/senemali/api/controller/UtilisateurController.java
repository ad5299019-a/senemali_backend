package com.senemali.api.controller;

import com.senemali.api.dto.request.UtilisateurRequest;
import com.senemali.api.dto.response.UtilisateurResponse;
import com.senemali.api.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs")
@SecurityRequirement(name = "bearerAuth")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister tous les utilisateurs")
    public ResponseEntity<List<UtilisateurResponse>> findAll() {
        return ResponseEntity.ok(utilisateurService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#id)")
    @Operation(summary = "Trouver un utilisateur par ID")
    public ResponseEntity<UtilisateurResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#id)")
    @Operation(summary = "Modifier un utilisateur (admin ou lui-même)")
    public ResponseEntity<UtilisateurResponse> update(@PathVariable Long id, @Valid @RequestBody UtilisateurRequest request) {
        return ResponseEntity.ok(utilisateurService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un utilisateur")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utilisateurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-statut")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activer/Désactiver un utilisateur")
    public ResponseEntity<UtilisateurResponse> toggleStatut(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.toggleStatut(id));
    }
}
