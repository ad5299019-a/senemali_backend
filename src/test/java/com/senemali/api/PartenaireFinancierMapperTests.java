package com.senemali.api;

import com.senemali.api.dto.request.PartenaireFinancierRequest;
import com.senemali.api.entity.PartenaireFinanciers;
import com.senemali.api.enums.TypePartenaireEnum;
import com.senemali.api.mapper.PartenaireFinancierMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartenaireFinancierMapperTests {

    private final PartenaireFinancierMapper mapper = new PartenaireFinancierMapper();

    @Test
    void mapsContactFieldsFromRequestToEntity() {
        PartenaireFinancierRequest request = new PartenaireFinancierRequest();
        request.setNomPartenaire("Banque agricole");
        request.setTypepartenaire(TypePartenaireEnum.BANQUE);
        request.setTelephone("+22370000000");
        request.setLocalisation("Bamako");
        request.setDescription("Financement agricole");

        PartenaireFinanciers partenaire = mapper.toEntity(request);

        assertEquals(request.getTelephone(), partenaire.getTelephone());
        assertEquals(request.getLocalisation(), partenaire.getLocalisation());
        assertEquals(request.getDescription(), partenaire.getDescription());
    }

    @Test
    void mapsContactFieldsFromEntityToResponse() {
        PartenaireFinanciers partenaire = PartenaireFinanciers.builder()
                .idPartenaire(1L)
                .nomPartenaire("Banque agricole")
                .typepartenaire(TypePartenaireEnum.BANQUE)
                .telephone("+22370000000")
                .localisation("Bamako")
                .description("Financement agricole")
                .build();

        var response = mapper.toResponse(partenaire);

        assertEquals(partenaire.getTelephone(), response.getTelephone());
        assertEquals(partenaire.getLocalisation(), response.getLocalisation());
        assertEquals(partenaire.getDescription(), response.getDescription());
    }
}
