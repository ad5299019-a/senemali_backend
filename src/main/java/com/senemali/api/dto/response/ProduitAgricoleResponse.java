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
public class ProduitAgricoleResponse {
    private Long idProduit;
    private String nomProduit;
    private String description;
    private int quantite;
    private int prix;
    private String unite;
    private Boolean disponible;
    private String imageUrl;
    private LocalDateTime datePub;
    private Long idAgriculteur;
    private String nomAgriculteur;
}
