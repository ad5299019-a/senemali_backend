package com.senemali.api.controller;

import com.senemali.api.dto.request.AuthRequest;
import com.senemali.api.dto.request.RegisterRequest;
import com.senemali.api.dto.response.AuthResponse;
import com.senemali.api.dto.response.UtilisateurResponse;
import com.senemali.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller d'authentification.
 * Routes publiques (pas de JWT requis).
 *
 * Utilisé par Angular et Flutter :
 * - POST /api/auth/login    → Connexion, retourne le token JWT
 * - POST /api/auth/register → Inscription (agriculteur, acheteur, fournisseur)
 * - POST /api/auth/refresh  → Renouveler le token (TODO)
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "Connexion et inscription des utilisateurs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "Se connecter",
            description = "Retourne un token JWT Bearer à inclure dans Authorization header"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Connexion réussie, token JWT retourné"),
            @ApiResponse(responseCode = "401", description = "Email/téléphone ou mot de passe incorrect"),
            @ApiResponse(responseCode = "403", description = "Compte désactivé")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(
            summary = "S'inscrire",
            description = "Créer un compte. Rôles possibles : AGRICULTEUR, ACHETEUR, FOURNISSEUR. Un fournisseur reste désactivé jusqu'à validation par un administrateur."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Compte créé avec succès"),
            @ApiResponse(responseCode = "409", description = "Email ou téléphone déjà utilisé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<UtilisateurResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Profil de l'utilisateur connecté",
            description = "Retourne les infos de l'utilisateur dont le JWT est dans le header"
    )
    public ResponseEntity<UtilisateurResponse> me(
            @RequestHeader("Authorization") String authHeader) {
        // Extraire le token du header Bearer
        String token = authHeader.substring(7);
        return ResponseEntity.ok(authService.getCurrentUser(authHeader.substring(7)));
    }
}
