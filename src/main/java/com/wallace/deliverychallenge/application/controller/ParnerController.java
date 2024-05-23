package com.wallace.deliverychallenge.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallace.deliverychallenge.application.service.PartnerService;
import com.wallace.deliverychallenge.domain.model.Partner;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/partner")
public class ParnerController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable("id") UUID id) {
        Partner partner = partnerService.getPartnerById(id);
        if (partner != null) {
            return ResponseEntity.ok(partner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Partner> getPartnerByCoordinate(@RequestParam Double longitude, @RequestParam Double latitude) {
        Partner partner = partnerService.getPartnerByCoordinate(longitude, latitude);
        if (partner != null) {
            return ResponseEntity.ok(partner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createPartner(@RequestBody String partnerJson) throws JsonProcessingException, SQLException, ParseException {
        try {
            partnerService.createPartner(partnerJson);
            return new ResponseEntity<>("Partner created succeffully!", HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePartner(@PathVariable("id") UUID id, @RequestBody String partnerJson) throws JsonProcessingException {
        try {
            partnerService.updatePartner(id, partnerJson);
            return new ResponseEntity<>("Partner updated succeffully!", HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable("id") UUID id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }
}
