package com.senemali.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions.
 * Retourne des réponses JSON cohérentes pour Angular et Flutter.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 404 - Ressource non trouvée */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now())
        );
    }

    /** 400 - Erreur métier ou données incorrectes */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Requête invalide ou données incorrectes", LocalDateTime.now())
        );
    }

    /** 400 - Erreurs de validation des champs (retourne chaque champ en erreur) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erreur de validation");
        response.put("message", "Des champs sont invalides");
        response.put("fields", fieldErrors);
        response.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /** 401 - Identifiants incorrects */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Email ou mot de passe incorrect", LocalDateTime.now())
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabled(DisabledException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Ce compte est désactivé", LocalDateTime.now())
        );
    }

    /** 403 - Accès interdit */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Accès refusé. Vous n'avez pas les permissions nécessaires.", LocalDateTime.now())
        );
    }

    /** 413 - Fichier trop volumineux */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(
                new ErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), "Fichier trop volumineux. Maximum: 10MB", LocalDateTime.now())
        );
    }

    /** 409 - Conflit de données (email/téléphone déjà utilisé, contrainte unique, etc.) */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataConflict(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(HttpStatus.CONFLICT.value(), "Conflit avec une donnée déjà existante", LocalDateTime.now())
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        String message = switch (status) {
            case CONFLICT -> "Une ressource avec ces informations existe déjà";
            case FORBIDDEN -> "Cette opération n'est pas autorisée";
            case NOT_FOUND -> "Ressource introuvable";
            case BAD_REQUEST -> "Requête invalide ou données incorrectes";
            default -> status.getReasonPhrase();
        };
        return ResponseEntity.status(status).body(
                new ErrorResponse(status.value(), message, LocalDateTime.now())
        );
    }

    /** 500 - Erreur interne */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur interne du serveur", LocalDateTime.now())
        );
    }
}
