package com.senemali.api.controller;

import com.senemali.api.dto.request.NotificationRequest;
import com.senemali.api.dto.response.NotificationResponse;
import com.senemali.api.service.NotificationService;
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
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Gestion des notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Envoyer une notification")
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(request));
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#idUtilisateur)")
    @Operation(summary = "Lister les notifications d'un utilisateur")
    public ResponseEntity<List<NotificationResponse>> findByUtilisateur(@PathVariable Long idUtilisateur) {
        return ResponseEntity.ok(notificationService.findByUtilisateur(idUtilisateur));
    }

    @GetMapping("/utilisateur/{idUtilisateur}/non-lues")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#idUtilisateur)")
    @Operation(summary = "Lister les notifications non lues")
    public ResponseEntity<List<NotificationResponse>> findNonLues(@PathVariable Long idUtilisateur) {
        return ResponseEntity.ok(notificationService.findNonLues(idUtilisateur));
    }

    @GetMapping("/utilisateur/{idUtilisateur}/count")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#idUtilisateur)")
    @Operation(summary = "Nombre de notifications non lues")
    public ResponseEntity<Long> countNonLues(@PathVariable Long idUtilisateur) {
        return ResponseEntity.ok(notificationService.countNonLues(idUtilisateur));
    }

    @PatchMapping("/{id}/lue")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isNotificationOwner(#id)")
    @Operation(summary = "Marquer comme lue")
    public ResponseEntity<NotificationResponse> markAsLue(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsLue(id));
    }

    @PatchMapping("/utilisateur/{idUtilisateur}/tout-lire")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#idUtilisateur)")
    @Operation(summary = "Marquer tout comme lu")
    public ResponseEntity<Void> markAllAsLue(@PathVariable Long idUtilisateur) {
        notificationService.markAllAsLue(idUtilisateur);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isNotificationOwner(#id)")
    @Operation(summary = "Supprimer une notification")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
