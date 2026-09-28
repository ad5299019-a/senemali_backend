package com.senemali.api.repository;

import com.senemali.api.entity.Commandes;
import com.senemali.api.enums.StatutCommandeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commandes, Long> {
    List<Commandes> findByUtilisateur_Id(Long idUtilisateur);
    List<Commandes> findByStatus(StatutCommandeEnum status);
}
