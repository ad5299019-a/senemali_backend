package com.senemali.api.dto.request;

import com.senemali.api.enums.TypePartenaireEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartenaireFinancierRequest {
    @NotBlank private String nomPartenaire;
    @NotNull private TypePartenaireEnum typepartenaire;
    @NotBlank private String description;
    @NotBlank private String localisation;
    @NotBlank private String telephone;
    private String imageUrl;
}
