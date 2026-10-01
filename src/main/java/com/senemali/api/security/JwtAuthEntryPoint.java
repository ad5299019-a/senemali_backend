package com.senemali.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senemali.api.reponse.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Gestionnaire d'erreur pour les requêtes non authentifiées.
 * Retourne une réponse JSON 401 lisible par Angular et Flutter.
 *
 * Retourne le même format que les réponses du contrôleur global.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiResponse<Void> body = new ApiResponse<>(
                false,
                HttpServletResponse.SC_UNAUTHORIZED,
                "Authentification requise ou token invalide",
                null,
                LocalDateTime.now()
        );
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
