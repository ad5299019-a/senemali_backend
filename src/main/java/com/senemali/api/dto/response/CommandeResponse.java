package com.senemali.api.dto.response;

import com.senemali.api.enums.StatutCommandeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommandeResponse {
    private Long idCommande;
    private LocalDateTime dateCommande;
    private StatutCommandeEnum status;
    private int montantTotal;
    private Long idUtilisateur;
    private String nomUtilisateur;
    private Long idVendeur;
    private String nomVendeur;
    private String roleVendeur;
    private List<DetailCommandeResponse> details;
}
