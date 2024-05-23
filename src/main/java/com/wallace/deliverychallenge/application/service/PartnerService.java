package com.wallace.deliverychallenge.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallace.deliverychallenge.domain.model.Partner;
import org.locationtech.jts.io.ParseException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface PartnerService {
    List<Partner> createOrUpdatePartner(String partnerJson) throws JsonProcessingException, ParseException;

    Partner getPartnerById(UUID id);

    void deletePartner(UUID id);

    Partner getPartnerByCoordinate(Double longitude, Double latitude);
}
