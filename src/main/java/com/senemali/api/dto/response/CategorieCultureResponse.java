package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorieCultureResponse {
    private Long idCategorie;
    private String nomCategorie;
    private String description;
    private List<CultureResponse> cultures;
}
