package com.senemali.api.controller;

import com.senemali.api.dto.request.ConseilRequest;
import com.senemali.api.dto.response.ConseilResponse;
import com.senemali.api.service.ConseilService;
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
@RequestMapping("/api/conseils")
@RequiredArgsConstructor
@Tag(name = "Conseils", description = "Gestion des conseils agricoles")
@SecurityRequirement(name = "bearerAuth")
public class ConseilController {

    private final ConseilService conseilService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Publier un conseil")
    public ResponseEntity<ConseilResponse> create(@Valid @RequestBody ConseilRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conseilService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les conseils")
    public ResponseEntity<List<ConseilResponse>> findAll() {
        return ResponseEntity.ok(conseilService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un conseil par ID")
    public ResponseEntity<ConseilResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(conseilService.findById(id));
    }

    @GetMapping("/culture/{idCulture}")
    @Operation(summary = "Lister les conseils d'une culture")
    public ResponseEntity<List<ConseilResponse>> findByCulture(@PathVariable Long idCulture) {
        return ResponseEntity.ok(conseilService.findByCulture(idCulture));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un conseil")
    public ResponseEntity<ConseilResponse> update(@PathVariable Long id, @Valid @RequestBody ConseilRequest request) {
        return ResponseEntity.ok(conseilService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un conseil")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        conseilService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
