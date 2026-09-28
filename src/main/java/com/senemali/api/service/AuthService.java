package com.senemali.api.service;

import com.senemali.api.dto.request.AuthRequest;
import com.senemali.api.dto.request.RegisterRequest;
import com.senemali.api.dto.response.AuthResponse;
import com.senemali.api.dto.response.UtilisateurResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UtilisateurResponse register(RegisterRequest request);

    /**
     * Retourne le profil de l'utilisateur connecté à partir de son token JWT.
     * Utilisé par le endpoint GET /api/auth/me
     */
    UtilisateurResponse getCurrentUser(String token);
}
