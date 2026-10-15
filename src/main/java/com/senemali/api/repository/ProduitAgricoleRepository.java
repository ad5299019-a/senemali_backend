package com.senemali.api.repository;

import com.senemali.api.entity.ProduitAgricoles;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProduitAgricoleRepository extends JpaRepository<ProduitAgricoles, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProduitAgricoles p where p.idProduit = :id")
    java.util.Optional<ProduitAgricoles> findByIdForUpdate(@Param("id") Long id);

    List<ProduitAgricoles> findByAgriculteur_Id(Long idAgriculteur);
    List<ProduitAgricoles> findByDisponible(Boolean disponible);
    List<ProduitAgricoles> findByNomProduitContainingIgnoreCase(String nomProduit);
}
