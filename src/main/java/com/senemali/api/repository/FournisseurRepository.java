package com.senemali.api.repository;

import com.senemali.api.entity.Fournisseurs;
import com.senemali.api.enums.StatutValidationFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseurs, Long> {
    List<Fournisseurs> findByStatutFournisseur(StatutValidationFournisseur statut);
}
