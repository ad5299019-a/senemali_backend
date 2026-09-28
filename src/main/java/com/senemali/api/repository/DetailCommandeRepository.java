package com.senemali.api.repository;

import com.senemali.api.entity.DetailCommandes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetailCommandeRepository extends JpaRepository<DetailCommandes, Long> {
    List<DetailCommandes> findByCommande_IdCommande(Long idCommande);
}
