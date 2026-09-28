package com.senemali.api.repository;

import com.senemali.api.entity.CategorieCultures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieCultureRepository extends JpaRepository<CategorieCultures, Long> {
    boolean existsByNomCategorie(String nomCategorie);
}
