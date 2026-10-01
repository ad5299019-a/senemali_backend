package com.senemali.api.security;

import com.senemali.api.entity.Utilisateurs;
import com.senemali.api.repository.UtilisateursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateursRepository utilisateursRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateurs utilisateur = utilisateursRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + email));
        return User.withUsername(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name())))
                .disabled(!Boolean.TRUE.equals(utilisateur.getStatut()))
                .build();
    }
}
