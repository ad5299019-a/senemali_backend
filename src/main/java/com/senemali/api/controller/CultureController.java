package com.senemali.api.controller;

import com.senemali.api.dto.request.CultureRequest;
import com.senemali.api.dto.response.CultureResponse;
import com.senemali.api.service.CultureService;
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
@RequestMapping("/api/cultures")
@RequiredArgsConstructor
@Tag(name = "Cultures", description = "Gestion des cultures")
@SecurityRequirement(name = "bearerAuth")
public class CultureController {

    private final CultureService cultureService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer une culture")
    public ResponseEntity<CultureResponse> create(@Valid @RequestBody CultureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cultureService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister toutes les cultures")
    public ResponseEntity<List<CultureResponse>> findAll() {
        return ResponseEntity.ok(cultureService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver une culture par ID")
    public ResponseEntity<CultureResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cultureService.findById(id));
    }

    @GetMapping("/categorie/{idCategorie}")
    @Operation(summary = "Lister les cultures d'une catégorie")
    public ResponseEntity<List<CultureResponse>> findByCategorie(@PathVariable Long idCategorie) {
        return ResponseEntity.ok(cultureService.findByCategorie(idCategorie));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier une culture")
    public ResponseEntity<CultureResponse> update(@PathVariable Long id, @Valid @RequestBody CultureRequest request) {
        return ResponseEntity.ok(cultureService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une culture")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cultureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
