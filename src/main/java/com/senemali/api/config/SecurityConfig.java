package com.senemali.api.config;

import com.senemali.api.security.JwtAuthFilter;
import com.senemali.api.security.JwtAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration de la sécurité Spring Security.
 *
 * Routes publiques (pas de JWT requis) :
 *  - Auth : /api/auth/**
 *  - Swagger : /swagger-ui/**, /v3/api-docs/**
 *  - Lecture publique : produits, conseils, tutoriels, prix marché, catégories
 *  - Upload statiques : /uploads/**
 *
 * Toutes les autres routes nécessitent un JWT valide.
 * Les autorisations par rôle sont gérées via @PreAuthorize dans les controllers.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    /** Routes accessibles sans authentification (GET public) */
    private static final String[] PUBLIC_GET_URLS = {
            "/api/produits/**",
            "/api/intrants/disponibles",
            "/api/conseils/**",
            "/api/tutoriels/**",
            "/api/prix-marche/**",
            "/api/categories-cultures/**",
            "/api/cultures/**",
            "/api/partenaires/**",
            "/api/services/disponibles",
            "/uploads/**"
    };

    /** Routes complètement publiques (toutes méthodes) */
    private static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/webjars/**",
            "/actuator/health"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        // Routes complètement publiques
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        // Routes en lecture publique
                        .requestMatchers(HttpMethod.GET, PUBLIC_GET_URLS).permitAll()
                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
