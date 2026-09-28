package com.senemali.api.dto.response;

import com.senemali.api.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcheteurResponse {
    private Long idAcheteur;
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String localisation;
    private String photoProfil;
    private RoleEnum role;
    private Boolean statut;
    private LocalDateTime dateCreation;
}
