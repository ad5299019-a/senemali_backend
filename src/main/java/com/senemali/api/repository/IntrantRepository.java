package com.senemali.api.repository;

import com.senemali.api.entity.Intrants;
import com.senemali.api.enums.StatutValidationFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IntrantRepository extends JpaRepository<Intrants, Long> {
    List<Intrants> findByFournisseur_Id(Long idFournisseur);
    List<Intrants> findByDisponible(Boolean disponible);
    List<Intrants> findByStatutIntrant(StatutValidationFournisseur statut);
}
