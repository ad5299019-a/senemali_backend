package com.senemali.api.reponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        String path = request.getURI().getPath();
        if (path.startsWith("/v3/api-docs") || path.startsWith("/api-docs")) {
            return body;
        }
        if (body == null || body instanceof ApiResponse<?>) {
            return body;
        }

        // Lit le statut HTTP réel depuis la réponse servlet utilisée par Spring MVC.
        int status = 200;
        if (response instanceof ServletServerHttpResponse servletResponse) {
            status = servletResponse.getServletResponse().getStatus();
        }
        boolean success = status < 400;
        String message = success
                ? getSuccessMessage(status, request.getMethod().name())
                : getErrorMessage(status);

        return new ApiResponse<>(success, status, message, body, LocalDateTime.now());
    }

    private String getSuccessMessage(int status, String method) {
        if (status == HttpStatus.CREATED.value()) return "Ressource créée avec succès";
        if (status == HttpStatus.ACCEPTED.value()) return "Requête acceptée";
        if (status == HttpStatus.NO_CONTENT.value()) return "Opération effectuée sans contenu";
        if (status == HttpStatus.OK.value()) {
            return switch (method) {
                case "GET" -> "Données récupérées avec succès";
                case "POST" -> "Opération effectuée avec succès";
                case "PUT" -> "Ressource modifiée avec succès";
                case "PATCH" -> "Ressource mise à jour avec succès";
                case "DELETE" -> "Ressource supprimée avec succès";
                default -> "Requête traitée avec succès";
            };
        }
        return "Requête traitée avec succès";
    }

    private String getErrorMessage(int status) {
        return switch (status) {
            case 400 -> "Requête invalide ou données incorrectes";
            case 401 -> "Authentification requise ou token invalide";
            case 403 -> "Accès refusé pour ce rôle ou cette ressource";
            case 404 -> "Ressource introuvable";
            case 405 -> "Méthode HTTP non autorisée pour cette ressource";
            case 409 -> "Conflit avec l’état actuel de la ressource";
            case 413 -> "Fichier trop volumineux";
            case 415 -> "Format de contenu non pris en charge";
            case 422 -> "Données reçues non traitables";
            case 429 -> "Trop de requêtes, veuillez réessayer plus tard";
            case 500 -> "Erreur interne du serveur";
            case 502 -> "Réponse invalide d’un service intermédiaire";
            case 503 -> "Service temporairement indisponible";
            case 504 -> "Délai d’attente du service dépassé";
            default -> "La requête a échoué (HTTP " + status + ")";
        };
    }
}