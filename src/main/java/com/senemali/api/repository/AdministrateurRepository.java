package com.senemali.api.repository;

import com.senemali.api.entity.Administrateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateurs, Long> {
    Optional<Administrateurs> findByEmail(String email);
}
