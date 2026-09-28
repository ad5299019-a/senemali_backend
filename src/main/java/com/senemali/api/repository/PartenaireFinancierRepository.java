package com.senemali.api.repository;

import com.senemali.api.entity.PartenaireFinanciers;
import com.senemali.api.enums.TypePartenaireEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartenaireFinancierRepository extends JpaRepository<PartenaireFinanciers, Long> {
    List<PartenaireFinanciers> findByTypepartenaire(TypePartenaireEnum type);
}
