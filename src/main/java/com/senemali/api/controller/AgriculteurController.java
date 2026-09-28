package com.senemali.api.controller;

import com.senemali.api.dto.request.AgriculteurRequest;
import com.senemali.api.dto.response.AgriculteurResponse;
import com.senemali.api.service.AgriculteurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agriculteurs")
@RequiredArgsConstructor
@Tag(name = "Agriculteurs", description = "Gestion des agriculteurs")
@SecurityRequirement(name = "bearerAuth")
public class AgriculteurController {

    private final AgriculteurService agriculteurService;

    @GetMapping
    @Operation(summary = "Lister tous les agriculteurs")
    public ResponseEntity<List<AgriculteurResponse>> findAll() {
        return ResponseEntity.ok(agriculteurService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un agriculteur par ID")
    public ResponseEntity<AgriculteurResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(agriculteurService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un agriculteur")
    public ResponseEntity<AgriculteurResponse> update(@PathVariable Long id, @Valid @RequestBody AgriculteurRequest request) {
        return ResponseEntity.ok(agriculteurService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un agriculteur")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agriculteurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
