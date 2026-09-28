package com.senemali.api.mapper;

import com.senemali.api.dto.request.CultureRequest;
import com.senemali.api.dto.response.CultureResponse;
import com.senemali.api.entity.Cultures;
import org.springframework.stereotype.Component;

@Component
public class CultureMapper {
    public Cultures toEntity(CultureRequest request) {
        Cultures culture = new Cultures();
        updateEntity(request, culture);
        return culture;
    }

    public void updateEntity(CultureRequest request, Cultures culture) {
        culture.setNomCulture(request.getNomCulture());
        culture.setDescription(request.getDescription());
    }

    public CultureResponse toResponse(Cultures culture) {
        return CultureResponse.builder()
                .idCulture(culture.getIdCulture())
                .nomCulture(culture.getNomCulture())
                .description(culture.getDescription())
                .idCategorie(culture.getCategorie() != null ? culture.getCategorie().getIdCategorie() : null)
                .nomCategorie(culture.getCategorie() != null ? culture.getCategorie().getNomCategorie() : null)
                .build();
    }
}