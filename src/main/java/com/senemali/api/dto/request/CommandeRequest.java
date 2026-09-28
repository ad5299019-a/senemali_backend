package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CommandeRequest {
    @NotNull private Long idUtilisateur;
    @NotNull private List<DetailCommandeRequest> details;
}
