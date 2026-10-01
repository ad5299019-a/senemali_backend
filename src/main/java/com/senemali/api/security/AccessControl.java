package com.senemali.api.security;

import com.senemali.api.entity.*;
import com.senemali.api.enums.RoleEnum;
import com.senemali.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("accessControl")
@RequiredArgsConstructor
public class AccessControl {

    private final UtilisateursRepository utilisateursRepository;
    private final ProduitAgricoleRepository produitRepository;
    private final IntrantRepository intrantRepository;
    private final CommandeRepository commandeRepository;
    private final NotificationRepository notificationRepository;
    private final CultureAgriculteurRepository cultureAgriculteurRepository;

    public boolean isCurrentUser(Long id) {
        return currentUser().map(user -> user.getId().equals(id)).orElse(false);
    }

    public boolean isCurrentAgriculteur(Long id) {
        return currentUser()
                .filter(user -> user.getRole() == RoleEnum.AGRICULTEUR)
                .map(user -> user.getId().equals(id))
                .orElse(false);
    }

    public boolean isCurrentFournisseur(Long id) {
        return currentUser()
                .filter(user -> user.getRole() == RoleEnum.FOURNISSEUR)
                .map(user -> user.getId().equals(id))
                .orElse(false);
    }

    public boolean isProductOwner(Long id) {
        return currentUser().flatMap(user -> produitRepository.findById(id)
                .map(produit -> produit.getAgriculteur().getId().equals(user.getId())))
                .orElse(false);
    }

    public boolean isIntrantOwner(Long id) {
        return currentUser().flatMap(user -> intrantRepository.findById(id)
                .map(intrant -> intrant.getFournisseur().getId().equals(user.getId())))
                .orElse(false);
    }

    public boolean isCommandeOwner(Long id) {
        return currentUser().flatMap(user -> commandeRepository.findById(id)
                .map(commande -> commande.getUtilisateur().getId().equals(user.getId())))
                .orElse(false);
    }

    public boolean isNotificationOwner(Long id) {
        return currentUser().flatMap(user -> notificationRepository.findById(id)
                .map(notification -> notification.getUtilisateur().getId().equals(user.getId())))
                .orElse(false);
    }

    public boolean isCultureAssociationOwner(Long id) {
        return currentUser()
                .filter(user -> user.getRole() == RoleEnum.AGRICULTEUR)
                .flatMap(user -> cultureAgriculteurRepository.findById(id)
                        .map(association -> association.getAgriculteur().getId().equals(user.getId())))
                .orElse(false);
    }

    public Long currentAdminId() {
        return currentUser()
                .filter(user -> user.getRole() == RoleEnum.ADMIN)
                .map(Utilisateurs::getId)
                .orElseThrow(() -> new AccessDeniedException("Action réservée aux administrateurs"));
    }

    private Optional<Utilisateurs> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }
        return utilisateursRepository.findByEmail(authentication.getName());
    }
}
