package com.senemali.api.repository;

import com.senemali.api.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findByUtilisateur_Id(Long idUtilisateur);
    List<Notifications> findByUtilisateur_IdAndLue(Long idUtilisateur, Boolean lue);
    long countByUtilisateur_IdAndLueFalse(Long idUtilisateur);
}
