package com.senemali.api.controller;

import com.senemali.api.dto.request.FinancementRequest;
import com.senemali.api.dto.response.FinancementResponse;
import com.senemali.api.service.FinancementService;
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
@RequestMapping("/api/financements")
@RequiredArgsConstructor
@Tag(name = "Financements", description = "Gestion des financements")
@SecurityRequirement(name = "bearerAuth")
public class FinancementController {

    private final FinancementService financementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un financement")
    public ResponseEntity<FinancementResponse> create(@Valid @RequestBody FinancementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financementService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les financements")
    public ResponseEntity<List<FinancementResponse>> findAll() {
        return ResponseEntity.ok(financementService.findAll());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister les financements disponibles")
    public ResponseEntity<List<FinancementResponse>> findDisponibles() {
        return ResponseEntity.ok(financementService.findDisponibles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un financement par ID")
    public ResponseEntity<FinancementResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(financementService.findById(id));
    }

    @GetMapping("/partenaire/{idPartenaire}")
    @Operation(summary = "Lister les financements d'un partenaire")
    public ResponseEntity<List<FinancementResponse>> findByPartenaire(@PathVariable Long idPartenaire) {
        return ResponseEntity.ok(financementService.findByPartenaire(idPartenaire));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un financement")
    public ResponseEntity<FinancementResponse> update(@PathVariable Long id, @Valid @RequestBody FinancementRequest request) {
        return ResponseEntity.ok(financementService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un financement")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        financementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
