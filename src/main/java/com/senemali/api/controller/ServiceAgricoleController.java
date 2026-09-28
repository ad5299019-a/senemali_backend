package com.senemali.api.controller;

import com.senemali.api.dto.request.ServiceAgricoleRequest;
import com.senemali.api.dto.response.ServiceAgricoleResponse;
import com.senemali.api.service.ServiceAgricoleService;
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
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Services Agricoles", description = "Gestion des services agricoles")
@SecurityRequirement(name = "bearerAuth")
public class ServiceAgricoleController {

    private final ServiceAgricoleService serviceAgricoleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un service")
    public ResponseEntity<ServiceAgricoleResponse> create(@Valid @RequestBody ServiceAgricoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceAgricoleService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les services")
    public ResponseEntity<List<ServiceAgricoleResponse>> findAll() {
        return ResponseEntity.ok(serviceAgricoleService.findAll());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister les services disponibles")
    public ResponseEntity<List<ServiceAgricoleResponse>> findDisponibles() {
        return ResponseEntity.ok(serviceAgricoleService.findDisponibles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un service par ID")
    public ResponseEntity<ServiceAgricoleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceAgricoleService.findById(id));
    }

    @GetMapping("/prestataire/{idPrestataire}")
    @Operation(summary = "Lister les services d'un prestataire")
    public ResponseEntity<List<ServiceAgricoleResponse>> findByPrestataire(@PathVariable Long idPrestataire) {
        return ResponseEntity.ok(serviceAgricoleService.findByPrestataire(idPrestataire));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un service")
    public ResponseEntity<ServiceAgricoleResponse> update(@PathVariable Long id, @Valid @RequestBody ServiceAgricoleRequest request) {
        return ResponseEntity.ok(serviceAgricoleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un service")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceAgricoleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
