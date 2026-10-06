package com.senemali.api;

import com.senemali.api.entity.Intrants;
import com.senemali.api.entity.ProduitAgricoles;
import com.senemali.api.mapper.IntrantMapper;
import com.senemali.api.mapper.ProduitAgricoleMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StockQuantityMapperTests {

    @Test
    void intrantResponseContainsStockQuantity() {
        Intrants intrant = Intrants.builder()
                .idIntrant(1L)
                .nomIntrant("Semences")
                .quantite(40)
                .build();

        assertEquals(40, new IntrantMapper().toResponse(intrant).getQuantite());
    }

    @Test
    void agriculturalProductResponseContainsStockQuantity() {
        ProduitAgricoles produit = ProduitAgricoles.builder()
                .idProduit(1L)
                .nomProduit("Mil")
                .quantite(75)
                .build();

        assertEquals(75, new ProduitAgricoleMapper().toResponse(produit).getQuantite());
    }
}
