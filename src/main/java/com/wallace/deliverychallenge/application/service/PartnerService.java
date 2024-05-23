package com.wallace.deliverychallenge.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallace.deliverychallenge.domain.model.Partner;
import org.locationtech.jts.io.ParseException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface PartnerService {
    void createPartner(String partnerJson) throws JsonProcessingException, ParseException;

    Partner getPartnerById(UUID id);
    void updatePartner(UUID id, String partnerJson) throws JsonProcessingException;

    void deletePartner(UUID id);

    Partner getPartnerByCoordinate(Double longitude, Double latitude);
}
