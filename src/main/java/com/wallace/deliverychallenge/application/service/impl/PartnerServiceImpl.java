package com.wallace.deliverychallenge.application.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallace.deliverychallenge.application.service.PartnerService;
import com.wallace.deliverychallenge.domain.model.Partner;
import com.wallace.deliverychallenge.domain.repository.PartnerRepository;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Value("${store.distance.range}")
    private double STORE_RANGE_KILOMETERS;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public List<Partner> createOrUpdatePartner(String partnerJson) throws JsonProcessingException, ParseException {
        ObjectMapper customObjectMapper = new ObjectMapper();
        List<Partner> partners = customObjectMapper.readValue(partnerJson, new TypeReference<List<Partner>>() {});

        for (Partner partner: partners) {
            partner.setDocument(partner.getDocument().replaceAll("[./]", ""));
        }
        return partnerRepository.saveAll(partners);
    }

    @Override
    public Partner getPartnerById(UUID id) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        return optionalPartner.orElse(null);
    }

    @Override
    public void deletePartner(UUID id) {
     partnerRepository.deleteById(id);
    }

    @Override
    public Partner getPartnerByCoordinate(Double longitude, Double latitude) {
        Distance distance = new Distance(STORE_RANGE_KILOMETERS, Metrics.KILOMETERS);
        return partnerRepository.findNearestPartner(longitude, latitude, distance.getValue());
    }
}
