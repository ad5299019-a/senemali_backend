package com.senemali.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration MVC pour servir les fichiers uploadés (images de profil, produits, etc.)
 * via l'URL : http://localhost:8080/uploads/nom-du-fichier
 *
 * Angular y accède via : environment.apiUrl + '/uploads/fichier.jpg'
 * Flutter y accède via : '$baseUrl/uploads/fichier.jpg'
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/")
                .setCachePeriod(3600);
    }
}
