package com.senemali.api.repository;

import com.senemali.api.entity.Commandes;
import com.senemali.api.enums.StatutCommandeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commandes, Long> {
    List<Commandes> findByUtilisateur_Id(Long idUtilisateur);
    List<Commandes> findByStatus(StatutCommandeEnum status);

    @Query("select distinct c from Commandes c join c.detailCommandes d where d.produit.agriculteur.id = :id")
    List<Commandes> findByAgriculteurVendeurId(@Param("id") Long id);

    @Query("select distinct c from Commandes c join c.detailCommandes d where d.intrant.fournisseur.id = :id")
    List<Commandes> findByFournisseurVendeurId(@Param("id") Long id);

    @Query("select count(distinct c.idCommande) from Commandes c join c.detailCommandes d where d.produit.agriculteur.id = :id")
    long countByAgriculteurVendeurId(@Param("id") Long id);

    @Query("select count(distinct c.idCommande) from Commandes c join c.detailCommandes d where d.intrant.fournisseur.id = :id")
    long countByFournisseurVendeurId(@Param("id") Long id);
}
