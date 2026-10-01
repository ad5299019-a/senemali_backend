package com.senemali.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name ="acheteurs")
@PrimaryKeyJoinColumn(name = "id_acheteur", referencedColumnName = "id")
@NoArgsConstructor
@SuperBuilder
public class Acheteurs extends Utilisateurs {
}
