package com.senemali.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now())
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

    /** 500 - Erreur interne */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur interne du serveur: " + ex.getMessage(), LocalDateTime.now())
        );
    }
}
