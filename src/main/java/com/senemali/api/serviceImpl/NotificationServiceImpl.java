package com.senemali.api.serviceImpl;

import com.senemali.api.dto.request.NotificationRequest;
import com.senemali.api.dto.response.NotificationResponse;
import com.senemali.api.entity.Notifications;
import com.senemali.api.entity.Utilisateurs;
import com.senemali.api.exception.ResourceNotFoundException;
import com.senemali.api.mapper.NotificationMapper;
import com.senemali.api.repository.NotificationRepository;
import com.senemali.api.repository.UtilisateursRepository;
import com.senemali.api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UtilisateursRepository utilisateursRepository;
    private final NotificationMapper mapper;

    @Override
    @Transactional
    public NotificationResponse create(NotificationRequest request) {
        Utilisateurs utilisateur = utilisateursRepository.findById(request.getIdUtilisateur())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        Notifications notification = mapper.toEntity(request);
        notification.setDateEnvoi(LocalDateTime.now());
        notification.setLue(false);
        notification.setUtilisateur(utilisateur);
        return mapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    public List<NotificationResponse> findByUtilisateur(Long idUtilisateur) {
        return notificationRepository.findByUtilisateur_Id(idUtilisateur).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> findNonLues(Long idUtilisateur) {
        return notificationRepository.findByUtilisateur_IdAndLue(idUtilisateur, false).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationResponse markAsLue(Long id) {
        Notifications notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification non trouvée"));
        notification.setLue(true);
        return mapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public void markAllAsLue(Long idUtilisateur) {
        List<Notifications> notifications = notificationRepository.findByUtilisateur_IdAndLue(idUtilisateur, false);
        notifications.forEach(n -> n.setLue(true));
        notificationRepository.saveAll(notifications);
    }

    @Override
    public long countNonLues(Long idUtilisateur) {
        return notificationRepository.countByUtilisateur_IdAndLueFalse(idUtilisateur);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!notificationRepository.existsById(id)) throw new ResourceNotFoundException("Notification non trouvée");
        notificationRepository.deleteById(id);
    }

}
