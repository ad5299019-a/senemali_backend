package com.senemali.api.dto.response;

import com.senemali.api.enums.TypeNotificationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Long idNotification;
    private String titre;
    private String message;
    private TypeNotificationEnum type;
    private LocalDateTime dateEnvoi;
    private Boolean lue;
    private Long idUtilisateur;
    private String nomUtilisateur;
}
