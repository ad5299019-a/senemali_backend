package com.senemali.api.controller;

import com.senemali.api.dto.request.PrestatireRequest;
import com.senemali.api.dto.response.PrestatireResponse;
import com.senemali.api.enums.TypePrestataireEnum;
import com.senemali.api.service.PrestatireService;
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
@RequestMapping("/api/prestataires")
@RequiredArgsConstructor
@Tag(name = "Prestataires", description = "Gestion des prestataires")
@SecurityRequirement(name = "bearerAuth")
public class PrestatireController {

    private final PrestatireService prestatireService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ajouter un prestataire")
    public ResponseEntity<PrestatireResponse> create(@Valid @RequestBody PrestatireRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestatireService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les prestataires")
    public ResponseEntity<List<PrestatireResponse>> findAll() {
        return ResponseEntity.ok(prestatireService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un prestataire par ID")
    public ResponseEntity<PrestatireResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(prestatireService.findById(id));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Lister par type")
    public ResponseEntity<List<PrestatireResponse>> findByType(@PathVariable TypePrestataireEnum type) {
        return ResponseEntity.ok(prestatireService.findByType(type));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un prestataire")
    public ResponseEntity<PrestatireResponse> update(@PathVariable Long id, @Valid @RequestBody PrestatireRequest request) {
        return ResponseEntity.ok(prestatireService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un prestataire")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prestatireService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
