package com.senemali.api.controller;

import com.senemali.api.dto.request.TutorielRequest;
import com.senemali.api.dto.response.TutorielResponse;
import com.senemali.api.service.TutorielService;
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
@RequestMapping("/api/tutoriels")
@RequiredArgsConstructor
@Tag(name = "Tutoriels", description = "Gestion des tutoriels")
@SecurityRequirement(name = "bearerAuth")
public class TutorielController {

    private final TutorielService tutorielService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un tutoriel")
    public ResponseEntity<TutorielResponse> create(@Valid @RequestBody TutorielRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorielService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les tutoriels")
    public ResponseEntity<List<TutorielResponse>> findAll() {
        return ResponseEntity.ok(tutorielService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un tutoriel par ID")
    public ResponseEntity<TutorielResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tutorielService.findById(id));
    }

    @GetMapping("/categorie/{idCategorie}")
    @Operation(summary = "Lister les tutoriels d'une catégorie")
    public ResponseEntity<List<TutorielResponse>> findByCategorie(@PathVariable Long idCategorie) {
        return ResponseEntity.ok(tutorielService.findByCategorie(idCategorie));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un tutoriel")
    public ResponseEntity<TutorielResponse> update(@PathVariable Long id, @Valid @RequestBody TutorielRequest request) {
        return ResponseEntity.ok(tutorielService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un tutoriel")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tutorielService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
