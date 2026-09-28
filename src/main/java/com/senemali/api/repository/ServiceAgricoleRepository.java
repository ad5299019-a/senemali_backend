package com.senemali.api.repository;

import com.senemali.api.entity.ServiceAgricoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceAgricoleRepository extends JpaRepository<ServiceAgricoles, Long> {
    List<ServiceAgricoles> findByPrestataire_IdPrestataire(Long idPrestataire);
    List<ServiceAgricoles> findByDisponible(Boolean disponible);
}
