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

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions.
 * Retourne des réponses JSON cohérentes pour Angular et Flutter.
 */
@RestControllerAdvice
@Slf4j
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
        log.error("Erreur d'exécution : ", ex);
        String message = ex.getMessage() != null && !ex.getMessage().isBlank()
                ? ex.getMessage()
                : "Requête invalide ou données incorrectes";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now())
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

        String detailMessage = fieldErrors.isEmpty()
                ? "Des champs sont invalides"
                : "Champs invalides : " + String.join(", ", fieldErrors.values());

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erreur de validation");
        response.put("message", detailMessage);
        response.put("fields", fieldErrors);
        response.put("errors", fieldErrors);
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
                new ErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), "Fichier trop volumineux (images : 10 MB ; vidéos de tutoriels : 100 MB)", LocalDateTime.now())
        );
    }

    /** 409 / 400 - Conflit de données ou violation de contrainte d'intégrité */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataConflict(DataIntegrityViolationException ex) {
        log.error("Violation d'intégrité des données : ", ex);
        String cause = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        if (cause != null && (cause.contains("Data too long") || cause.contains("too long"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Le contenu d'un champ dépasse la taille maximale autorisée (ex: description ou image trop longue).", LocalDateTime.now())
            );
        }
        if (cause != null && cause.contains("Duplicate entry")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorResponse(HttpStatus.CONFLICT.value(), "Cette information existe déjà dans le système.", LocalDateTime.now())
            );
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(HttpStatus.CONFLICT.value(), "Conflit avec une donnée existante ou contrainte non respectée", LocalDateTime.now())
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
            case PAYLOAD_TOO_LARGE -> "Fichier trop volumineux (images : 10 MB ; vidéos de tutoriels : 100 MB)";
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
