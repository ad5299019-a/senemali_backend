package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CultureResponse {
    private Long idCulture;
    private String nomCulture;
    private String description;
    private String imageUrl;
    private Long idCategorie;
    private String nomCategorie;
}
