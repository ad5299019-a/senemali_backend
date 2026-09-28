package com.senemali.api.repository;

import com.senemali.api.entity.Prestataires;
import com.senemali.api.enums.TypePrestataireEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestatireRepository extends JpaRepository<Prestataires, Long> {
    List<Prestataires> findByTypePrestataire(TypePrestataireEnum type);
}
