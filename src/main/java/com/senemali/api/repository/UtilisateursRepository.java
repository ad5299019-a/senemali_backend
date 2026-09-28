package com.senemali.api.repository;

import com.senemali.api.entity.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateurs, Long> {
    Optional<Utilisateurs> findByEmail(String email);
    Optional<Utilisateurs> findByTelephone(String telephone);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
}
