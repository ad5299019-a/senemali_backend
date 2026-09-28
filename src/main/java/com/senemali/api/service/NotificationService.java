package com.senemali.api.service;

import com.senemali.api.dto.request.NotificationRequest;
import com.senemali.api.dto.response.NotificationResponse;
import java.util.List;

public interface NotificationService {
    NotificationResponse create(NotificationRequest request);
    List<NotificationResponse> findByUtilisateur(Long idUtilisateur);
    List<NotificationResponse> findNonLues(Long idUtilisateur);
    NotificationResponse markAsLue(Long id);
    void markAllAsLue(Long idUtilisateur);
    long countNonLues(Long idUtilisateur);
    void delete(Long id);
}
