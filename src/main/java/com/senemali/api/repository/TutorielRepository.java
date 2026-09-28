package com.senemali.api.repository;

import com.senemali.api.entity.Tutoriels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TutorielRepository extends JpaRepository<Tutoriels, Long> {
    List<Tutoriels> findByCategorie_IdCategorie(Long idCategorie);
}
