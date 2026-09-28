package com.senemali.api.repository;

import com.senemali.api.entity.Cultures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CultureRepository extends JpaRepository<Cultures, Long> {
    List<Cultures> findByCategorie_IdCategorie(Long idCategorie);
}
