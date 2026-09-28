package com.senemali.api.mapper;

import com.senemali.api.dto.request.ConseilRequest;
import com.senemali.api.dto.response.ConseilResponse;
import com.senemali.api.entity.Conseils;
import org.springframework.stereotype.Component;

@Component
public class ConseilMapper {
    public Conseils toEntity(ConseilRequest request) {
        Conseils conseil = new Conseils();
        updateEntity(request, conseil);
        return conseil;
    }

    public void updateEntity(ConseilRequest request, Conseils conseil) {
        conseil.setTitreConseil(request.getTitreConseil());
        conseil.setContenu(request.getContenu());
    }

    public ConseilResponse toResponse(Conseils conseil) {
        return ConseilResponse.builder()
                .idConseil(conseil.getIdConseil())
                .titreConseil(conseil.getTitreConseil())
                .contenu(conseil.getContenu())
                .datePub(conseil.getDatePub())
                .idCulture(conseil.getCulture() != null ? conseil.getCulture().getIdCulture() : null)
                .nomCulture(conseil.getCulture() != null ? conseil.getCulture().getNomCulture() : null)
                .idAdministrateur(conseil.getAdministrateur() != null
                        ? conseil.getAdministrateur().getIdAdmistrateur() : null)
                .build();
    }
}