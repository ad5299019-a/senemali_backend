package com.senemali.api.dto.response;

import com.senemali.api.enums.TypePartenaireEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartenaireFinancierResponse {
    private Long idPartenaire;
    private String nomPartenaire;
    private TypePartenaireEnum typepartenaire;
    private String description;
    private String localisation;
    private String telephone;
    private String imageUrl;
    private List<FinancementResponse> financements;
}
