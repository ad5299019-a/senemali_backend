package com.senemali.api.dto.request;

import com.senemali.api.enums.TypePartenaireEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartenaireFinancierRequest {
    @NotBlank private String nomPartenaire;
    @NotNull private TypePartenaireEnum typepartenaire;
    private String description;
    private String localisation;
    private String telephone;
    private String imageUrl;
    @NotNull private Long idAdministrateur;
}
