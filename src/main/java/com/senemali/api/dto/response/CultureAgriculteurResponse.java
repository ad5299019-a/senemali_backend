package com.senemali.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CultureAgriculteurResponse {
    private Long idCultureAgriculteur;
    private Long idAgriculteur;
    private String nomAgriculteur;
    private Long idCulture;
    private String nomCulture;
}
