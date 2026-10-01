package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class CommandeRequest {
    @NotNull private Long idUtilisateur;
    @NotEmpty private List<DetailCommandeRequest> details;
}
