package com.senemali.api.repository;

import com.senemali.api.entity.CultureAgriculteurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CultureAgriculteurRepository extends JpaRepository<CultureAgriculteurs, Long> {
    List<CultureAgriculteurs> findByAgriculteur_Id(Long idAgriculteur);
    List<CultureAgriculteurs> findByCulture_IdCulture(Long idCulture);
    boolean existsByAgriculteur_IdAndCulture_IdCulture(Long idAgriculteur, Long idCulture);
}
