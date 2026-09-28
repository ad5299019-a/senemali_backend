package com.senemali.api.mapper;

import com.senemali.api.dto.response.CultureAgriculteurResponse;
import com.senemali.api.entity.Agriculteurs;
import com.senemali.api.entity.CultureAgriculteurs;
import com.senemali.api.entity.Cultures;
import org.springframework.stereotype.Component;

@Component
public class CultureAgriculteurMapper {
    public CultureAgriculteurs toEntity(Agriculteurs agriculteur, Cultures culture) {
        return CultureAgriculteurs.builder()
                .agriculteur(agriculteur)
                .culture(culture)
                .build();
    }

    public CultureAgriculteurResponse toResponse(CultureAgriculteurs association) {
        return CultureAgriculteurResponse.builder()
                .idCultureAgriculteur(association.getIdCultureAgriculteur())
                .idAgriculteur(association.getAgriculteur() != null
                        ? association.getAgriculteur().getIdAgriculteur() : null)
                .nomAgriculteur(association.getAgriculteur() != null
                        ? association.getAgriculteur().getNom() : null)
                .idCulture(association.getCulture() != null ? association.getCulture().getIdCulture() : null)
                .nomCulture(association.getCulture() != null ? association.getCulture().getNomCulture() : null)
                .build();
    }
}