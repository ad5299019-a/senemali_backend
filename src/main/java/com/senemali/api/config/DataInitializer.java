package com.senemali.api.config;

import com.senemali.api.entity.Administrateurs;
import com.senemali.api.enums.RoleEnum;
import com.senemali.api.repository.AdministrateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Initialisation des données de base au démarrage de l'application.
 * Crée un compte ADMIN au premier démarrage lorsque les identifiants de bootstrap
 * sont fournis par la configuration d'environnement.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:senemali@gmail.com}")
    private String adminEmail;

    @Value("${app.admin.password:1234}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (administrateurRepository.count() == 0) {
            if (adminEmail.isBlank() || adminPassword.isBlank()) {
                log.warn("Aucun compte administrateur créé : configurez APP_ADMIN_EMAIL et APP_ADMIN_PASSWORD.");
                return;
            }
            Administrateurs admin = Administrateurs.builder()
                    .nom("Admin")
                    .prenom("Senemali")
                    .email(adminEmail)
                    .telephone("+22300000000")
                    .localisation("Bamako, Mali")
                    .motDePasse(passwordEncoder.encode(adminPassword))
                    .role(RoleEnum.ADMIN)
                    .statut(true)
                    .dateCreation(LocalDateTime.now())
                    .build();
            administrateurRepository.save(admin);
            log.info("Compte administrateur initialisé pour {}", adminEmail);
        } else {
            if (!adminEmail.isBlank() && !adminPassword.isBlank()) {
                administrateurRepository.findByEmail(adminEmail).ifPresent(admin -> {
                    if (!passwordEncoder.matches(adminPassword, admin.getMotDePasse())) {
                        admin.setMotDePasse(passwordEncoder.encode(adminPassword));
                        administrateurRepository.save(admin);
                        log.info("Mot de passe administrateur réinitialisé depuis la configuration.");
                    }
                });
            }
            log.info("Un compte administrateur existe déjà, initialisation ignorée.");
        }
    }
}
