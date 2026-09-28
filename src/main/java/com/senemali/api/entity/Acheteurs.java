package com.senemali.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name ="acheteurs")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Acheteurs extends Utilisateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAcheteur;

}
