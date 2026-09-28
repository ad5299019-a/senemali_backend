package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrixMarcheResponse {
    private Long idPrix;
    private String nomProduit;
    private int prixProduit;
    private String unite;
    private String localisation;
    private LocalDateTime dateObserv;
    private Long idAdministrateur;
}
