package com.senemali.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire d'erreur pour les requêtes non authentifiées.
 * Retourne une réponse JSON 401 lisible par Angular et Flutter.
 *
 * Réponse JSON :
 * {
 *   "status": 401,
 *   "error": "Non autorisé",
 *   "message": "Token JWT manquant ou invalide",
 *   "path": "/api/..."
 * }
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Non autorisé");
        body.put("message", "Token JWT manquant ou invalide. Veuillez vous connecter.");
        body.put("path", request.getServletPath());
        body.put("timestamp", System.currentTimeMillis());

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
