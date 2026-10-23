package com.senemali.api.repository;

import com.senemali.api.entity.Intrants;
import com.senemali.api.enums.StatutValidationFournisseur;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IntrantRepository extends JpaRepository<Intrants, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Intrants i where i.idIntrant = :id")
    java.util.Optional<Intrants> findByIdForUpdate(@Param("id") Long id);

    List<Intrants> findByFournisseur_Id(Long idFournisseur);
    List<Intrants> findByDisponible(Boolean disponible);
    List<Intrants> findByStatutIntrant(StatutValidationFournisseur statut);
}
