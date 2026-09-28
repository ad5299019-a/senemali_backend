package com.senemali.api.repository;

import com.senemali.api.entity.PrixMarche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrixMarcheRepository extends JpaRepository<PrixMarche, Long> {
    List<PrixMarche> findByNomProduitContainingIgnoreCase(String nomProduit);
    List<PrixMarche> findByLocalisation(String localisation);
}
