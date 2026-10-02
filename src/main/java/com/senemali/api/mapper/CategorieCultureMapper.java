package com.senemali.api.mapper;

import com.senemali.api.dto.request.CategorieCultureRequest;
import com.senemali.api.dto.response.CategorieCultureResponse;
import com.senemali.api.dto.response.CultureResponse;
import com.senemali.api.entity.CategorieCultures;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorieCultureMapper {
    public CategorieCultures toEntity(CategorieCultureRequest request) {
        CategorieCultures categorie = new CategorieCultures();
        updateEntity(request, categorie);
        return categorie;
    }

    public void updateEntity(CategorieCultureRequest request, CategorieCultures categorie) {
        categorie.setNomCategorie(request.getNomCategorie());
        categorie.setDescription(request.getDescription());
    }

    public CategorieCultureResponse toResponse(CategorieCultures categorie) {
        List<CultureResponse> cultures = categorie.getCultures() == null ? List.of() : categorie.getCultures().stream()
                .map(culture -> CultureResponse.builder()
                        .idCulture(culture.getIdCulture())
                        .nomCulture(culture.getNomCulture())
                        .description(culture.getDescription())
                        .imageUrl(culture.getImageUrl())
                        .idCategorie(categorie.getIdCategorie())
                        .nomCategorie(categorie.getNomCategorie())
                        .build())
                .toList();
        return CategorieCultureResponse.builder()
                .idCategorie(categorie.getIdCategorie())
                .nomCategorie(categorie.getNomCategorie())
                .description(categorie.getDescription())
                .cultures(cultures)
                .build();
    }
}