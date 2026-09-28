package com.senemali.api.dto.request;

import com.senemali.api.enums.TypeNotificationEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {
    @NotBlank private String titre;
    @NotBlank private String message;
    @NotNull private TypeNotificationEnum type;
    @NotNull private Long idUtilisateur;
}
