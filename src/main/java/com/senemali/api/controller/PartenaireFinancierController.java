package com.senemali.api.controller;

import com.senemali.api.dto.request.PartenaireFinancierRequest;
import com.senemali.api.dto.response.PartenaireFinancierResponse;
import com.senemali.api.enums.TypePartenaireEnum;
import com.senemali.api.service.PartenaireFinancierService;
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
@RequestMapping("/api/partenaires")
@RequiredArgsConstructor
@Tag(name = "Partenaires Financiers", description = "Gestion des partenaires financiers")
@SecurityRequirement(name = "bearerAuth")
public class PartenaireFinancierController {

    private final PartenaireFinancierService partenaireService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ajouter un partenaire")
    public ResponseEntity<PartenaireFinancierResponse> create(@Valid @RequestBody PartenaireFinancierRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partenaireService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les partenaires")
    public ResponseEntity<List<PartenaireFinancierResponse>> findAll() {
        return ResponseEntity.ok(partenaireService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un partenaire par ID")
    public ResponseEntity<PartenaireFinancierResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(partenaireService.findById(id));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Lister par type")
    public ResponseEntity<List<PartenaireFinancierResponse>> findByType(@PathVariable TypePartenaireEnum type) {
        return ResponseEntity.ok(partenaireService.findByType(type));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un partenaire")
    public ResponseEntity<PartenaireFinancierResponse> update(@PathVariable Long id, @Valid @RequestBody PartenaireFinancierRequest request) {
        return ResponseEntity.ok(partenaireService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un partenaire")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partenaireService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
