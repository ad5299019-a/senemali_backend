package com.senemali.api.repository;

import com.senemali.api.entity.Conseils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConseilRepository extends JpaRepository<Conseils, Long> {
    List<Conseils> findByCulture_IdCulture(Long idCulture);
}
