package com.wallace.deliverychallenge.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallace.deliverychallenge.domain.model.Partner;
import com.wallace.deliverychallenge.infraestructure.exception.ExceptionError;
import org.locationtech.jts.io.ParseException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface PartnerService {
    List<Partner> createOrUpdatePartner(String partnerJson) throws ExceptionError;

    Partner getPartnerById(UUID id) throws ExceptionError;

    void deletePartner(UUID id) throws ExceptionError;

    Partner getPartnerByCoordinate(Double longitude, Double latitude);
}
