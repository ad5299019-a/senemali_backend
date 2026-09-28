package com.senemali.api.controller;

import com.senemali.api.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Controller pour l'upload de fichiers (images).
 *
 * Utilisé par Angular et Flutter pour uploader :
 * - Photos de profil des utilisateurs
 * - Images des produits agricoles
 * - Images des intrants
 * - Miniatures des tutoriels
 *
 * Flutter exemple :
 *   final request = http.MultipartRequest('POST', Uri.parse('$baseUrl/api/files/upload/produits'));
 *   request.files.add(await http.MultipartFile.fromPath('file', filePath));
 *
 * Angular exemple :
 *   formData.append('file', file);
 *   this.http.post('/api/files/upload/produits', formData)
 */
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "Upload Fichiers", description = "Upload d'images (profils, produits, tutoriels)")
@SecurityRequirement(name = "bearerAuth")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @PostMapping("/upload/{folder}")
    @Operation(
            summary = "Uploader une image",
            description = "Folders disponibles: profils, produits, intrants, tutoriels, services"
    )
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable String folder) {

        // Valider le sous-dossier autorisé
        if (!isValidFolder(folder)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Dossier non autorisé. Utilisez: profils, produits, intrants, tutoriels, services"
            ));
        }

        String fileName = fileStorageService.saveFile(file, folder);
        String fileUrl = fileStorageService.getFileUrl(fileName, folder);

        return ResponseEntity.ok(Map.of(
                "fileName", fileName,
                "fileUrl", fileUrl,
                "folder", folder,
                "message", "Fichier uploadé avec succès"
        ));
    }

    @DeleteMapping("/delete/{folder}/{fileName}")
    @Operation(summary = "Supprimer un fichier uploadé")
    public ResponseEntity<Map<String, String>> deleteFile(
            @PathVariable String folder,
            @PathVariable String fileName) {

        if (!isValidFolder(folder)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Dossier non autorisé"));
        }

        fileStorageService.deleteFile(fileName, folder);
        return ResponseEntity.ok(Map.of("message", "Fichier supprimé avec succès"));
    }

    private boolean isValidFolder(String folder) {
        return folder != null && folder.matches("profils|produits|intrants|tutoriels|services|conseils");
    }
}
