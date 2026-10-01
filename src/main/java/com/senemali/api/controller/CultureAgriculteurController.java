package com.senemali.api.controller;

import com.senemali.api.dto.request.CultureAgriculteurRequest;
import com.senemali.api.dto.response.CultureAgriculteurResponse;
import com.senemali.api.service.CultureAgriculteurService;
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
@RequestMapping("/api/cultures-agriculteurs")
@RequiredArgsConstructor
@Tag(name = "Cultures des agriculteurs", description = "Cultures sélectionnées par les agriculteurs")
@SecurityRequirement(name = "bearerAuth")
public class CultureAgriculteurController {

    private final CultureAgriculteurService cultureAgriculteurService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('AGRICULTEUR') and @accessControl.isCurrentAgriculteur(#request.idAgriculteur))")
    @Operation(summary = "Associer une culture à un agriculteur")
    public ResponseEntity<CultureAgriculteurResponse> add(
            @Valid @RequestBody CultureAgriculteurRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cultureAgriculteurService.add(request));
    }

    @GetMapping("/agriculteur/{idAgriculteur}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('AGRICULTEUR') and @accessControl.isCurrentAgriculteur(#idAgriculteur))")
    @Operation(summary = "Lister les cultures sélectionnées par un agriculteur")
    public ResponseEntity<List<CultureAgriculteurResponse>> findByAgriculteur(
            @PathVariable Long idAgriculteur) {
        return ResponseEntity.ok(cultureAgriculteurService.findByAgriculteur(idAgriculteur));
    }

    @GetMapping("/culture/{idCulture}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister les agriculteurs associés à une culture (admin uniquement)")
    public ResponseEntity<List<CultureAgriculteurResponse>> findByCulture(
            @PathVariable Long idCulture) {
        return ResponseEntity.ok(cultureAgriculteurService.findByCulture(idCulture));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCultureAssociationOwner(#id)")
    @Operation(summary = "Retirer une culture de la sélection")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        cultureAgriculteurService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
