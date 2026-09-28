package com.senemali.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "cultures_agriculteurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CultureAgriculteurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCultureAgriculteur;

    @ManyToOne
    @JoinColumn(name = "id_agriculteur", nullable = false)
    private Agriculteurs agriculteur;

    @ManyToOne
    @JoinColumn(name = "id_culture", nullable = false)
    private Cultures culture;
}
