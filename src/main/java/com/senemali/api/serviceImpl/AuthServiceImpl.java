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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import com.senemali.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
                .orElseThrow(() -> new BadCredentialsException("Identifiants incorrects"));
        if (!passwordEncoder.matches(request.getMotDePasse(), utilisateur.getMotDePasse())) {
            throw new BadCredentialsException("Identifiants incorrects");
        }
        if (!Boolean.TRUE.equals(utilisateur.getStatut())) {
            throw new DisabledException("Compte désactivé");
        }
        if (utilisateur instanceof Fournisseurs fournisseur
                && fournisseur.getStatutFournisseur() != StatutValidationFournisseur.VALIDE) {
            throw new DisabledException("Compte fournisseur en attente de validation");
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
        if (request.getRole() == RoleEnum.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La création d'un compte administrateur est interdite");
        }
        if (utilisateursRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
        }
        if (utilisateursRepository.existsByTelephone(request.getTelephone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Téléphone déjà utilisé");
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
                    .statut(false)
                    .dateCreation(LocalDateTime.now())
                    .description(request.getDescriptionFournisseur())
                    .nomEntrepise(request.getNomEntreprise())
                    .zoneDeVente(request.getZoneDeVente())
                    .statutFournisseur(StatutValidationFournisseur.EN_ATTENTE)
                    .build();
            Fournisseurs saved = fournisseurRepository.save(fournisseur);
            return utilisateurMapper.toResponse(saved);
        } else if (RoleEnum.ACHETEUR.equals(request.getRole())) {
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
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rôle d'inscription non pris en charge");
    }

    @Override
    public UtilisateurResponse getCurrentUser(String token) {
        String email = jwtUtil.extractEmail(token);
        Utilisateurs utilisateur = utilisateursRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return utilisateurMapper.toResponse(utilisateur);
    }
}
