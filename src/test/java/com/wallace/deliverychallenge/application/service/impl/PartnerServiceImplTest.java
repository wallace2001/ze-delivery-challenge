package com.wallace.deliverychallenge.application.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallace.deliverychallenge.application.fixture.PartnerFixture;
import com.wallace.deliverychallenge.domain.model.Partner;
import com.wallace.deliverychallenge.domain.repository.PartnerRepository;
import com.wallace.deliverychallenge.infraestructure.exception.ExceptionError;
import com.wallace.deliverychallenge.utils.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartnerServiceImplTest {

    private List<Partner> partners;

    private DataLoader dataLoader;
    private ObjectMapper objectMapper;

    @InjectMocks
    PartnerServiceImpl partnerService;

    @Mock
    PartnerRepository partnerRepository;

    @BeforeEach
    void setUp() throws Exception {
        this.dataLoader = new DataLoader();
        this.objectMapper = new ObjectMapper();
        PartnerFixture partnerFixture = new PartnerFixture(dataLoader);
        partners = partnerFixture.createPartners();
    }

    @Test
    void should_SaveAllPartnersInJsonAndReturn_When_CreateOrUpdatePartnerCalled() throws Exception {
        when(partnerRepository.saveAll(any())).thenReturn(partners);
        String jsonPartner = dataLoader.loadDataRaw();
        List<Partner> partners = objectMapper.readValue(jsonPartner, new TypeReference<List<Partner>>() {});

        List<Partner> orUpdatePartner = partnerService.createOrUpdatePartner(jsonPartner);

        assertNotNull(orUpdatePartner);

        for (Partner partner : orUpdatePartner) {
            assertNotNull(partner.getId());
            assertEquals(partner.getTradingName(), partners.get(0).getTradingName());
            assertEquals(partner.getOwnerName(), partners.get(0).getOwnerName());
            assertEquals(partner.getDocument(), partners.get(0).getDocument());
            assertEquals(partner.getAddress(), partners.get(0).getAddress());
            assertEquals(partner.getCoverageArea(), partners.get(0).getCoverageArea());
        }
    }

    @Test
    void should_returnPartner_When_getPartnerById() throws ExceptionError {
        Partner partner = partners.get(0);
        when(partnerRepository.findById(any())).thenReturn(Optional.of(partner));

        Partner partnerById = partnerService.getPartnerById(partner.getId());

        assertEquals(partnerById.getId(), partner.getId());
        assertEquals(partnerById.getTradingName(), partner.getTradingName());
        assertEquals(partnerById.getOwnerName(), partner.getOwnerName());
        assertEquals(partnerById.getDocument(), partner.getDocument());
        assertEquals(partnerById.getAddress(), partner.getAddress());
        assertEquals(partnerById.getCoverageArea(), partner.getCoverageArea());
    }

    @Test
    void deletePartner() throws ExceptionError {
        Partner partner = partners.get(0);
        partnerService.deletePartner(partner.getId());

        verify(partnerRepository).deleteById(partner.getId());
    }

    @Test
    void getPartnerByCoordinate() {
        Partner partner = partners.get(0);
        when(partnerRepository.findNearestPartner(anyDouble(), anyDouble(), anyDouble())).thenReturn(partner);

        Partner partnerByCoordinate = partnerService.getPartnerByCoordinate(-23.9999, -22.9999);

        assertEquals(partnerByCoordinate.getId(), partner.getId());
        assertEquals(partnerByCoordinate.getTradingName(), partner.getTradingName());
        assertEquals(partnerByCoordinate.getOwnerName(), partner.getOwnerName());
        assertEquals(partnerByCoordinate.getDocument(), partner.getDocument());
        assertEquals(partnerByCoordinate.getAddress(), partner.getAddress());
        assertEquals(partnerByCoordinate.getCoverageArea(), partner.getCoverageArea());
    }
}