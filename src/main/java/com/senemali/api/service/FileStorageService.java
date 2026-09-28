package com.senemali.api.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service de gestion des fichiers uploadés (images profil, produits, etc.)
 * Utilisé par Angular et Flutter pour uploader des images.
 */
public interface FileStorageService {

    /**
     * Sauvegarde un fichier dans le dossier uploads/
     * @param file le fichier multipart
     * @param subFolder sous-dossier (ex: "profils", "produits")
     * @return le nom du fichier sauvegardé
     */
    String saveFile(MultipartFile file, String subFolder);

    /**
     * Supprime un fichier du dossier uploads/
     * @param fileName nom du fichier
     * @param subFolder sous-dossier
     */
    void deleteFile(String fileName, String subFolder);

    /**
     * Retourne l'URL publique d'un fichier
     * @param fileName nom du fichier
     * @param subFolder sous-dossier
     * @return URL complète (ex: http://localhost:8080/uploads/produits/image.jpg)
     */
    String getFileUrl(String fileName, String subFolder);
}
