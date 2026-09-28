package com.senemali.api.controller;

import com.senemali.api.dto.request.CategorieCultureRequest;
import com.senemali.api.dto.response.CategorieCultureResponse;
import com.senemali.api.service.CategorieCultureService;
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
@RequestMapping("/api/categories-cultures")
@RequiredArgsConstructor
@Tag(name = "Catégories de Cultures", description = "Gestion des catégories de cultures")
@SecurityRequirement(name = "bearerAuth")
public class CategorieCultureController {

    private final CategorieCultureService categorieService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer une catégorie")
    public ResponseEntity<CategorieCultureResponse> create(@Valid @RequestBody CategorieCultureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister toutes les catégories")
    public ResponseEntity<List<CategorieCultureResponse>> findAll() {
        return ResponseEntity.ok(categorieService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver une catégorie par ID")
    public ResponseEntity<CategorieCultureResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier une catégorie")
    public ResponseEntity<CategorieCultureResponse> update(@PathVariable Long id, @Valid @RequestBody CategorieCultureRequest request) {
        return ResponseEntity.ok(categorieService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une catégorie")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
