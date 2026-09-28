package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategorieCultureRequest {
    @NotBlank private String nomCategorie;
    private String description;
}
