package com.senemali.api.repository;

import com.senemali.api.entity.Acheteurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcheteurRepository extends JpaRepository<Acheteurs, Long> {
}
