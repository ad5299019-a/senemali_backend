package com.senemali.api.entity;


import com.senemali.api.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "utilisateurs")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 35)
    private String nom;

    @Column(nullable = false, length = 35)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String telephone;

    @Column(nullable = false, length = 35)
    private String localisation;

    @Column(nullable = false, length = 255)
    private String motDePasse;

    private String photoProfil;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private Boolean statut;
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notifications> notifications;

    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commandes> commandes;
}
