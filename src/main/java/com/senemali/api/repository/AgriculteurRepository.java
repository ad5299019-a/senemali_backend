package com.senemali.api.repository;

import com.senemali.api.entity.Agriculteurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgriculteurRepository extends JpaRepository<Agriculteurs, Long> {
}
