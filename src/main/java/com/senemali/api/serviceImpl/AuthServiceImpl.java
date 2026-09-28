package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.AuthRequest;
import com.senemali.api.dto.request.RegisterRequest;
import com.senemali.api.dto.response.AuthResponse;
import com.senemali.api.dto.response.UtilisateurResponse;
import com.senemali.api.entity.*;
import com.senemali.api.enums.RoleEnum;
import com.senemali.api.enums.StatutValidationFournisseur;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.UtilisateurMapper;
import com.senemali.api.repository.*;
import com.senemali.api.security.JwtUtil;
import com.senemali.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UtilisateursRepository utilisateursRepository;
    private final AgriculteurRepository agriculteurRepository;
    private final AcheteurRepository acheteurRepository;
    private final FournisseurRepository fournisseurRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurMapper utilisateurMapper;

    @Override
    public AuthResponse login(AuthRequest request) {
        Utilisateurs utilisateur = utilisateursRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        if (!passwordEncoder.matches(request.getMotDePasse(), utilisateur.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        if (Boolean.FALSE.equals(utilisateur.getStatut())) {
            throw new RuntimeException("Compte désactivé");
        }
        String token = jwtUtil.generateToken(utilisateur.getEmail(), utilisateur.getRole().name());
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .role(utilisateur.getRole())
                .build();
    }

    @Override
    @Transactional
    public UtilisateurResponse register(RegisterRequest request) {
        if (utilisateursRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        if (utilisateursRepository.existsByTelephone(request.getTelephone())) {
            throw new RuntimeException("Téléphone déjà utilisé");
        }
        String encodedPassword = passwordEncoder.encode(request.getMotDePasse());
        if (RoleEnum.AGRICULTEUR.equals(request.getRole())) {
            Agriculteurs agriculteur = Agriculteurs.builder()
                    .nom(request.getNom())
                    .prenom(request.getPrenom())
                    .email(request.getEmail())
                    .telephone(request.getTelephone())
                    .localisation(request.getLocalisation())
                    .motDePasse(encodedPassword)
                    .photoProfil(request.getPhotoProfil())
                    .role(RoleEnum.AGRICULTEUR)
                    .statut(true)
                    .dateCreation(LocalDateTime.now())
                    .descripion(request.getDescriptionAgriculteur())
                    .build();
            Agriculteurs saved = agriculteurRepository.save(agriculteur);
            return utilisateurMapper.toResponse(saved);
        } else if (RoleEnum.FOURNISSEUR.equals(request.getRole())) {
            Fournisseurs fournisseur = Fournisseurs.builder()
                    .nom(request.getNom())
                    .prenom(request.getPrenom())
                    .email(request.getEmail())
                    .telephone(request.getTelephone())
                    .localisation(request.getLocalisation())
                    .motDePasse(encodedPassword)
                    .photoProfil(request.getPhotoProfil())
                    .role(RoleEnum.FOURNISSEUR)
                    .statut(true)
                    .dateCreation(LocalDateTime.now())
                    .description(request.getDescriptionFournisseur())
                    .nomEntrepise(request.getNomEntreprise())
                    .zoneDeVente(request.getZoneDeVente())
                    .statutFournisseur(StatutValidationFournisseur.EN_ATTENTE)
                    .build();
            Fournisseurs saved = fournisseurRepository.save(fournisseur);
            return utilisateurMapper.toResponse(saved);
        } else {
            Acheteurs acheteur = Acheteurs.builder()
                    .nom(request.getNom())
                    .prenom(request.getPrenom())
                    .email(request.getEmail())
                    .telephone(request.getTelephone())
                    .localisation(request.getLocalisation())
                    .motDePasse(encodedPassword)
                    .photoProfil(request.getPhotoProfil())
                    .role(RoleEnum.ACHETEUR)
                    .statut(true)
                    .dateCreation(LocalDateTime.now())
                    .build();
            Acheteurs saved = acheteurRepository.save(acheteur);
            return utilisateurMapper.toResponse(saved);
        }
    }

    @Override
    public UtilisateurResponse getCurrentUser(String token) {
        String email = jwtUtil.extractEmail(token);
        Utilisateurs utilisateur = utilisateursRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return utilisateurMapper.toResponse(utilisateur);
    }
}
