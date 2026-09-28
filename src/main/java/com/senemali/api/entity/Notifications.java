package com.senemali.api.entity;


import com.senemali.api.enums.TypeNotificationEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.plaf.basic.BasicButtonUI;
import java.io.StringReader;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idNotification;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private TypeNotificationEnum type;


    private LocalDateTime dateEnvoi;

    @Column(nullable = false)
    private Boolean lue;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateurs utilisateur;
}
