package com.senemali.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SenemaliApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void businessRoutesRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/produits"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    @WithMockUser(roles = "ACHETEUR")
    void onlyAdminsCanListAllOrders() throws Exception {
        mockMvc.perform(get("/api/commandes"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(403));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminsCannotCreateOrders() throws Exception {
        mockMvc.perform(post("/api/commandes")
                        .contentType("application/json")
                        .content("{\"details\":[{\"idProduit\":1,\"quantite\":1}]}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ACHETEUR")
    void onlyFarmersAndSuppliersCanReadReceivedOrderCount() throws Exception {
        mockMvc.perform(get("/api/commandes/mes-commandes/count"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ACHETEUR")
    void onlyAdminsAndFarmersCanListFinancialPartners() throws Exception {
        mockMvc.perform(get("/api/partenaires"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ACHETEUR")
    void onlyAdminsAndFarmersCanListProviders() throws Exception {
        mockMvc.perform(get("/api/prestataires"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "AGRICULTEUR")
    void categoryListCanLoadItsCultures() throws Exception {
        mockMvc.perform(get("/api/categories-cultures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void openApiDocumentIsServedAsRawSpecification() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.openapi").exists())
                .andExpect(jsonPath("$.paths['/api/auth/login']").exists());
    }

    @Test
    void swaggerConfigurationUsesLocalOpenApiDocument() throws Exception {
        mockMvc.perform(get("/v3/api-docs/swagger-config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("/v3/api-docs"));
    }
}
