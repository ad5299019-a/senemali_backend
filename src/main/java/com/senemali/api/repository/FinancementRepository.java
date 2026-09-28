package com.senemali.api.repository;

import com.senemali.api.entity.Financements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FinancementRepository extends JpaRepository<Financements, Long> {
    List<Financements> findByPartenaire_IdPartenaire(Long idPartenaire);
    List<Financements> findByDisponible(Boolean disponible);
}
