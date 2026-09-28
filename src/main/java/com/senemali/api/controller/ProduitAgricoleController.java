package com.senemali.api.controller;

import com.senemali.api.dto.request.ProduitAgricoleRequest;
import com.senemali.api.dto.response.ProduitAgricoleResponse;
import com.senemali.api.service.ProduitAgricoleService;
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
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(name = "Produits Agricoles", description = "Gestion des produits agricoles")
@SecurityRequirement(name = "bearerAuth")
public class ProduitAgricoleController {

    private final ProduitAgricoleService produitService;

    @PostMapping
    @PreAuthorize("hasAnyRole('AGRICULTEUR', 'ADMIN')")
    @Operation(summary = "Ajouter un produit")
    public ResponseEntity<ProduitAgricoleResponse> create(@Valid @RequestBody ProduitAgricoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les produits")
    public ResponseEntity<List<ProduitAgricoleResponse>> findAll() {
        return ResponseEntity.ok(produitService.findAll());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister les produits disponibles")
    public ResponseEntity<List<ProduitAgricoleResponse>> findDisponibles() {
        return ResponseEntity.ok(produitService.findDisponibles());
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des produits par nom")
    public ResponseEntity<List<ProduitAgricoleResponse>> search(@RequestParam String nom) {
        return ResponseEntity.ok(produitService.search(nom));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un produit par ID")
    public ResponseEntity<ProduitAgricoleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.findById(id));
    }

    @GetMapping("/agriculteur/{idAgriculteur}")
    @Operation(summary = "Lister les produits d'un agriculteur")
    public ResponseEntity<List<ProduitAgricoleResponse>> findByAgriculteur(@PathVariable Long idAgriculteur) {
        return ResponseEntity.ok(produitService.findByAgriculteur(idAgriculteur));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AGRICULTEUR', 'ADMIN')")
    @Operation(summary = "Modifier un produit")
    public ResponseEntity<ProduitAgricoleResponse> update(@PathVariable Long id, @Valid @RequestBody ProduitAgricoleRequest request) {
        return ResponseEntity.ok(produitService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('AGRICULTEUR', 'ADMIN')")
    @Operation(summary = "Supprimer un produit")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
