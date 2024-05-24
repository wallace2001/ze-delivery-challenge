package com.wallace.deliverychallenge.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallace.deliverychallenge.application.fixture.PartnerFixture;
import com.wallace.deliverychallenge.application.service.PartnerService;
import com.wallace.deliverychallenge.domain.model.Partner;
import com.wallace.deliverychallenge.utils.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PartnerController.class)
class PartnerControllerTest {
    private DataLoader dataLoader;
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<Partner> partners;

    @MockBean
    private PartnerService partnerService;

    @BeforeEach
    void setUp() throws Exception {
        this.dataLoader = new DataLoader();
        this.objectMapper = new ObjectMapper();
        PartnerFixture partnerFixture = new PartnerFixture(dataLoader);
        partners = partnerFixture.createPartners();
    }

    @Test
    void getPartnerById_shouldReturnPartner() throws Exception {
        Partner partner = partners.get(0);
        Mockito.when(partnerService.getPartnerById(partner.getId())).thenReturn(partner);

        mockMvc.perform(get("/partner/{id}", partner.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(partner.getId().toString()));
    }

    @Test
    void getPartnerById_shouldReturnNotFound() throws Exception {
        Partner partner = partners.get(0);
        Mockito.when(partnerService.getPartnerById(partner.getId())).thenReturn(null);

        mockMvc.perform(get("/partner/{id}", partner.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Partner not found with id: " + partner.getId()));
    }

    @Test
    void getPartnerByCoordinate_shouldReturnPartner() throws Exception {
        Double longitude = -43.297337;
        Double latitude = -23.013538;
        Partner partner = partners.get(0);
        Mockito.when(partnerService.getPartnerByCoordinate(longitude, latitude)).thenReturn(partner);

        mockMvc.perform(get("/partner")
                        .param("longitude", longitude.toString())
                        .param("latitude", latitude.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getPartnerByCoordinate_shouldReturnNotFound() throws Exception {
        Double longitude = -43.297337;
        Double latitude = -23.013538;
        Mockito.when(partnerService.getPartnerByCoordinate(longitude, latitude)).thenReturn(null);

        mockMvc.perform(get("/partner")
                        .param("longitude", longitude.toString())
                        .param("latitude", latitude.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPartner_shouldReturnCreated() throws Exception {
        Partner partner = partners.get(0);
        String jsonPartner = dataLoader.loadDataRaw();
        Mockito.when(partnerService.createOrUpdatePartner(jsonPartner)).thenReturn(Collections.singletonList(partner));

        mockMvc.perform(post("/partner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPartner))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(partner.getId().toString()));
    }

    @Test
    void createPartner_shouldReturnInternalServerError() throws Exception {
        String errorMessage = "Erro interno do servidor";
        String partnerJson = objectMapper.writeValueAsString(Collections.singletonList(new Partner()));
        Mockito.when(partnerService.createOrUpdatePartner(partnerJson)).thenThrow(new RuntimeException(errorMessage));

        mockMvc.perform(post("/partner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partnerJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(errorMessage));
    }

    @Test
    public void updatePartner_shouldReturnCreated() throws Exception {
        Partner partner = partners.get(0);
        String jsonPartner = dataLoader.loadDataRaw();
        Mockito.when(partnerService.createOrUpdatePartner(jsonPartner)).thenReturn(Collections.singletonList(partner));

        mockMvc.perform(put("/partner/{id}", partner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPartner))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(partner.getId().toString()));
    }

    @Test
    public void updatePartner_shouldReturnInternalServerError() throws Exception {
        String errorMessage = "Erro interno do servidor";
        Partner partner = partners.get(0);
        String jsonPartner = dataLoader.loadDataRaw();
        Mockito.when(partnerService.createOrUpdatePartner(jsonPartner)).thenThrow(new RuntimeException(errorMessage));

        mockMvc.perform(put("/partner/{id}", partner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPartner))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(errorMessage));
    }

    @Test
    void deletePartner_shouldReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/partner/{id}", id))
                .andExpect(status().isNoContent());
    }
}