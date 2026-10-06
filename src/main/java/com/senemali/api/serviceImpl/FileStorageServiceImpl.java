package com.senemali.api.serviceImpl;

import com.senemali.api.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Implémentation du service de stockage des fichiers.
 * Stocke les fichiers dans le dossier local {app.upload.dir}/
 *
 * Structure des dossiers :
 *   uploads/
 *     profils/       → photos de profil utilisateurs
 *     produits/      → images produits agricoles
 *     intrants/      → images intrants
 *     tutoriels/     → images/vidéos tutoriels
 */
@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "gif", "webp", "svg"
    );

    private static final List<String> ALLOWED_VIDEO_EXTENSIONS = Arrays.asList("mp4", "webm", "mov");

    @Value("${app.upload.max-size:10485760}")
    private long maxImageSize;

    @Value("${app.upload.max-video-size:104857600}")
    private long maxVideoSize;

    @Override
    public String saveFile(MultipartFile file, String subFolder) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide");
        }
        String originalFilename = StringUtils.cleanPath(
                file.getOriginalFilename() != null ? file.getOriginalFilename() : "file"
        );
        String extension = getExtension(originalFilename).toLowerCase();
        boolean video = "tutoriels".equals(subFolder) && ALLOWED_VIDEO_EXTENSIONS.contains(extension);
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension) && !video) {
            List<String> allowedExtensions = "tutoriels".equals(subFolder)
                    ? Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "svg", "mp4", "webm", "mov")
                    : ALLOWED_IMAGE_EXTENSIONS;
            throw new RuntimeException("Format de fichier non supporté. Utilisez: " + allowedExtensions);
        }
        long maxFileSize = video ? maxVideoSize : maxImageSize;
        if (file.getSize() > maxFileSize) {
            long maxSizeMb = maxFileSize / (1024 * 1024);
            throw new ResponseStatusException(
                    HttpStatus.PAYLOAD_TOO_LARGE,
                    "Fichier trop volumineux (maximum " + maxSizeMb + " MB)"
            );
        }

        // Générer un nom unique pour éviter les conflits
        String newFileName = UUID.randomUUID() + "." + extension;

        try {
            Path targetDir = Paths.get(uploadDir, subFolder).toAbsolutePath().normalize();
            Files.createDirectories(targetDir);

            Path targetPath = targetDir.resolve(newFileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Fichier sauvegardé : {}/{}", subFolder, newFileName);
            return newFileName;
        } catch (IOException e) {
            log.error("Erreur lors de la sauvegarde du fichier", e);
            throw new RuntimeException("Impossible de sauvegarder le fichier: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileName, String subFolder) {
        if (fileName == null || fileName.isBlank()) return;
        try {
            Path filePath = Paths.get(uploadDir, subFolder, fileName).toAbsolutePath().normalize();
            Files.deleteIfExists(filePath);
            log.info("Fichier supprimé : {}/{}", subFolder, fileName);
        } catch (IOException e) {
            log.warn("Impossible de supprimer le fichier {}/{}: {}", subFolder, fileName, e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String fileName, String subFolder) {
        if (fileName == null || fileName.isBlank()) return null;
        return baseUrl + "/uploads/" + subFolder + "/" + fileName;
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex > 0 ? filename.substring(dotIndex + 1) : "";
    }
}
