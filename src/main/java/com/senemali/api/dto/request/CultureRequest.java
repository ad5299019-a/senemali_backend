package com.senemali.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CultureRequest {
    @NotBlank private String nomCulture;
    private String description;
    private String imageUrl;
    @NotNull private Long idCategorie;
}
