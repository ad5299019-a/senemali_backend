package com.senemali.api.mapper;

import com.senemali.api.dto.request.NotificationRequest;
import com.senemali.api.dto.response.NotificationResponse;
import com.senemali.api.entity.Notifications;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notifications toEntity(NotificationRequest request) {
        return Notifications.builder()
                .titre(request.getTitre())
                .message(request.getMessage())
                .type(request.getType())
                .build();
    }

    public NotificationResponse toResponse(Notifications notification) {
        return NotificationResponse.builder()
                .idNotification(notification.getIdNotification())
                .titre(notification.getTitre())
                .message(notification.getMessage())
                .type(notification.getType())
                .dateEnvoi(notification.getDateEnvoi())
                .lue(notification.getLue())
                .idUtilisateur(notification.getUtilisateur() != null ? notification.getUtilisateur().getId() : null)
                .nomUtilisateur(notification.getUtilisateur() != null
                        ? notification.getUtilisateur().getNom() : null)
                .build();
    }
}