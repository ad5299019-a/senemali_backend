package com.senemali.api.controller;

import com.senemali.api.dto.response.AcheteurResponse;
import com.senemali.api.dto.request.AcheteursRequest;
import com.senemali.api.service.AcheteurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour la gestion des acheteurs.
 * Un acheteur est un utilisateur avec le rôle ACHETEUR.
 */
@RestController
@RequestMapping("/api/acheteurs")
@RequiredArgsConstructor
@Tag(name = "Acheteurs", description = "Gestion des acheteurs")
@SecurityRequirement(name = "bearerAuth")
public class AcheteurController {

    private final AcheteurService acheteurService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister tous les acheteurs")
    public ResponseEntity<List<AcheteurResponse>> findAll() {
        return ResponseEntity.ok(acheteurService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un acheteur par ID")
    public ResponseEntity<AcheteurResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(acheteurService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un acheteur")
    public ResponseEntity<AcheteurResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody AcheteursRequest request) {
        return ResponseEntity.ok(acheteurService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un acheteur")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        acheteurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
