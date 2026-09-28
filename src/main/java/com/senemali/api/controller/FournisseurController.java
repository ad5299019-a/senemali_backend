package com.senemali.api.controller;

import com.senemali.api.dto.request.FournisseurRequest;
import com.senemali.api.dto.response.FournisseurResponse;
import com.senemali.api.enums.StatutValidationFournisseur;
import com.senemali.api.service.FournisseurService;
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
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
@Tag(name = "Fournisseurs", description = "Gestion des fournisseurs")
@SecurityRequirement(name = "bearerAuth")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister tous les fournisseurs")
    public ResponseEntity<List<FournisseurResponse>> findAll() {
        return ResponseEntity.ok(fournisseurService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un fournisseur par ID")
    public ResponseEntity<FournisseurResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un fournisseur")
    public ResponseEntity<FournisseurResponse> update(@PathVariable Long id, @Valid @RequestBody FournisseurRequest request) {
        return ResponseEntity.ok(fournisseurService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un fournisseur")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fournisseurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/valider")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Valider/Rejeter un fournisseur")
    public ResponseEntity<FournisseurResponse> valider(@PathVariable Long id,
            @RequestParam Long idAdmin, @RequestParam StatutValidationFournisseur statut) {
        return ResponseEntity.ok(fournisseurService.validerFournisseur(id, idAdmin, statut));
    }

    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister par statut")
    public ResponseEntity<List<FournisseurResponse>> findByStatut(@PathVariable StatutValidationFournisseur statut) {
        return ResponseEntity.ok(fournisseurService.findByStatut(statut));
    }
}
