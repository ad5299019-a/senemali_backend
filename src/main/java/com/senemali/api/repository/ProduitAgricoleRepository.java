package com.senemali.api.repository;

import com.senemali.api.entity.ProduitAgricoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProduitAgricoleRepository extends JpaRepository<ProduitAgricoles, Long> {
    List<ProduitAgricoles> findByAgriculteur_Id(Long idAgriculteur);
    List<ProduitAgricoles> findByDisponible(Boolean disponible);
    List<ProduitAgricoles> findByNomProduitContainingIgnoreCase(String nomProduit);
}
