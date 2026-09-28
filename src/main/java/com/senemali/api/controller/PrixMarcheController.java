package com.senemali.api.controller;

import com.senemali.api.dto.request.PrixMarcheRequest;
import com.senemali.api.dto.response.PrixMarcheResponse;
import com.senemali.api.service.PrixMarcheService;
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
@RequestMapping("/api/prix-marche")
@RequiredArgsConstructor
@Tag(name = "Prix Marché", description = "Gestion des prix du marché")
@SecurityRequirement(name = "bearerAuth")
public class PrixMarcheController {

    private final PrixMarcheService prixMarcheService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Enregistrer un prix")
    public ResponseEntity<PrixMarcheResponse> create(@Valid @RequestBody PrixMarcheRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prixMarcheService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les prix")
    public ResponseEntity<List<PrixMarcheResponse>> findAll() {
        return ResponseEntity.ok(prixMarcheService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un prix par ID")
    public ResponseEntity<PrixMarcheResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(prixMarcheService.findById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher par nom de produit")
    public ResponseEntity<List<PrixMarcheResponse>> findByNom(@RequestParam String nom) {
        return ResponseEntity.ok(prixMarcheService.findByNomProduit(nom));
    }

    @GetMapping("/localisation")
    @Operation(summary = "Rechercher par localisation")
    public ResponseEntity<List<PrixMarcheResponse>> findByLocalisation(@RequestParam String localisation) {
        return ResponseEntity.ok(prixMarcheService.findByLocalisation(localisation));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un prix")
    public ResponseEntity<PrixMarcheResponse> update(@PathVariable Long id, @Valid @RequestBody PrixMarcheRequest request) {
        return ResponseEntity.ok(prixMarcheService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un prix")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prixMarcheService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
