package com.senemali.api.config;

import com.senemali.api.entity.Administrateurs;
import com.senemali.api.enums.RoleEnum;
import com.senemali.api.repository.AdministrateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Initialisation des données de base au démarrage de l'application.
 * Crée un compte ADMIN par défaut s'il n'en existe aucun.
 *
 * Compte admin par défaut :
 *   Email    : admin@senemali.ml
 *   Password : Admin@2024
 *
 * ⚠️ IMPORTANT : Changer le mot de passe en production !
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (administrateurRepository.count() == 0) {
            Administrateurs admin = Administrateurs.builder()
                    .nom("Admin")
                    .prenom("Senemali")
                    .email("admin@senemali.ml")
                    .telephone("+22300000000")
                    .localisation("Bamako, Mali")
                    .motDePasse(passwordEncoder.encode("Admin@2024"))
                    .role(RoleEnum.ADMIN)
                    .statut(true)
                    .dateCreation(LocalDateTime.now())
                    .build();
            administrateurRepository.save(admin);
            log.info("✅ Compte Admin créé : admin@senemali.ml / Admin@2024");
        } else {
            log.info("✅ Admin déjà existant, skip initialisation.");
        }
    }
}
