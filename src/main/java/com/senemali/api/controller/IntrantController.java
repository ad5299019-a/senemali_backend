package com.senemali.api.controller;

import com.senemali.api.dto.request.IntrantRequest;
import com.senemali.api.dto.response.IntrantResponse;
import com.senemali.api.enums.StatutValidationFournisseur;
import com.senemali.api.service.IntrantService;
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
@RequestMapping("/api/intrants")
@RequiredArgsConstructor
@Tag(name = "Intrants", description = "Gestion des intrants agricoles")
@SecurityRequirement(name = "bearerAuth")
public class IntrantController {

    private final IntrantService intrantService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('FOURNISSEUR') and @accessControl.isCurrentFournisseur(#request.idFournisseur))")
    @Operation(summary = "Ajouter un intrant")
    public ResponseEntity<IntrantResponse> create(@Valid @RequestBody IntrantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(intrantService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les intrants")
    public ResponseEntity<List<IntrantResponse>> findAll() {
        return ResponseEntity.ok(intrantService.findAll());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister les intrants disponibles")
    public ResponseEntity<List<IntrantResponse>> findDisponibles() {
        return ResponseEntity.ok(intrantService.findDisponibles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un intrant par ID")
    public ResponseEntity<IntrantResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(intrantService.findById(id));
    }

    @GetMapping("/fournisseur/{idFournisseur}")
    @Operation(summary = "Lister les intrants d'un fournisseur")
    public ResponseEntity<List<IntrantResponse>> findByFournisseur(@PathVariable Long idFournisseur) {
        return ResponseEntity.ok(intrantService.findByFournisseur(idFournisseur));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('FOURNISSEUR') and @accessControl.isIntrantOwner(#id))")
    @Operation(summary = "Modifier un intrant")
    public ResponseEntity<IntrantResponse> update(@PathVariable Long id, @Valid @RequestBody IntrantRequest request) {
        return ResponseEntity.ok(intrantService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('FOURNISSEUR') and @accessControl.isIntrantOwner(#id))")
    @Operation(summary = "Supprimer un intrant")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        intrantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/valider")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Valider un intrant")
    public ResponseEntity<IntrantResponse> valider(@PathVariable Long id,
            @RequestParam StatutValidationFournisseur statut) {
        return ResponseEntity.ok(intrantService.validerIntrant(id, statut));
    }
}
