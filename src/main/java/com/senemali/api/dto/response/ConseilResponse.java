package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConseilResponse {
    private Long idConseil;
    private String titreConseil;
    private String contenu;
    private LocalDate datePub;
    private Long idCulture;
    private String nomCulture;
    private Long idAdministrateur;
}
