package com.wallace.deliverychallenge.application.controller;

import com.wallace.deliverychallenge.application.service.PartnerService;
import com.wallace.deliverychallenge.domain.model.Partner;
import com.wallace.deliverychallenge.infraestructure.exception.ExceptionError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the book",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Partner.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Book not found",
                content = @Content) })
@RestController
@RequestMapping("/partner")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPartnerById(@PathVariable("id") UUID id) throws ExceptionError {
        Partner partner = partnerService.getPartnerById(id);
        if (partner != null) {
            return ResponseEntity.ok(partner);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partner not found with id: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<Partner> getPartnerByCoordinate(
            @RequestParam Double longitude,
            @RequestParam Double latitude
    ) {
        Partner partner = partnerService.getPartnerByCoordinate(longitude, latitude);
        if (partner != null) {
            return ResponseEntity.ok(partner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createPartner(
            @Schema(name = "Json Partner", example = "[\n" +
                    "        {\n" +
                    "        \"tradingName\": \"Adega Osasco\",\n" +
                    "        \"ownerName\": \"Ze da Ambev\",\n" +
                    "        \"document\": \"02.453.716/000170\",\n" +
                    "        \"coverageArea\": {\n" +
                    "            \"type\": \"MultiPolygon\",\n" +
                    "            \"coordinates\": [\n" +
                    "                [\n" +
                    "                    [\n" +
                    "                        [-43.36556, -22.99669],\n" +
                    "                        [-43.36539, -23.01928],\n" +
                    "                        [-43.26583, -23.01802],\n" +
                    "                        [-43.25724, -23.00649],\n" +
                    "                        [-43.23355, -23.00127],\n" +
                    "                        [-43.2381, -22.99716],\n" +
                    "                        [-43.23866, -22.99649],\n" +
                    "                        [-43.24063, -22.99756],\n" +
                    "                        [-43.24634, -22.99736],\n" +
                    "                        [-43.24677, -22.99606],\n" +
                    "                        [-43.24067, -22.99381],\n" +
                    "                        [-43.24886, -22.99121],\n" +
                    "                        [-43.25617, -22.99456],\n" +
                    "                        [-43.25625, -22.99203],\n" +
                    "                        [-43.25346, -22.99065],\n" +
                    "                        [-43.29599, -22.98283],\n" +
                    "                        [-43.3262, -22.96481],\n" +
                    "                        [-43.33427, -22.96402],\n" +
                    "                        [-43.33616, -22.96829],\n" +
                    "                        [-43.342, -22.98157],\n" +
                    "                        [-43.34817, -22.97967],\n" +
                    "                        [-43.35142, -22.98062],\n" +
                    "                        [-43.3573, -22.98084],\n" +
                    "                        [-43.36522, -22.98032],\n" +
                    "                        [-43.36696, -22.98422],\n" +
                    "                        [-43.36717, -22.98855],\n" +
                    "                        [-43.36636, -22.99351],\n" +
                    "                        [-43.36556, -22.99669]\n" +
                    "                    ]\n" +
                    "                ]\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"address\": {\n" +
                    "            \"type\": \"Point\",\n" +
                    "            \"coordinates\": [-43.297337, -23.013538]\n" +
                    "        }\n" +
                    "    }\n" +
                    "]")
            @RequestBody String partnerJson
    ) throws ExceptionError {
        try {
            List<Partner> partners = partnerService.createOrUpdatePartner(partnerJson);
            return new ResponseEntity<>(partners, HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePartner(
            @Schema(name = "Update Partner", example = "[\n" +
                    "    {\n" +
                    "        \"id\": \"idPartner\",\n" +
                    "        \"tradingName\": \"Adega Osasco 5\",\n" +
                    "        \"ownerName\": \"Ze da Ambev\",\n" +
                    "        \"document\": \"02453716000170\",\n" +
                    "        \"coverageArea\": {\n" +
                    "            \"type\": \"MultiPolygon\",\n" +
                    "            \"coordinates\": [\n" +
                    "                [\n" +
                    "                    [\n" +
                    "                        [\n" +
                    "                            -43.36556,\n" +
                    "                            -22.99669\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.36539,\n" +
                    "                            -23.01928\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.26583,\n" +
                    "                            -23.01802\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.25724,\n" +
                    "                            -23.00649\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.23355,\n" +
                    "                            -23.00127\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.2381,\n" +
                    "                            -22.99716\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.23866,\n" +
                    "                            -22.99649\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.24063,\n" +
                    "                            -22.99756\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.24634,\n" +
                    "                            -22.99736\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.24677,\n" +
                    "                            -22.99606\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.24067,\n" +
                    "                            -22.99381\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.24886,\n" +
                    "                            -22.99121\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.25617,\n" +
                    "                            -22.99456\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.25625,\n" +
                    "                            -22.99203\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.25346,\n" +
                    "                            -22.99065\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.29599,\n" +
                    "                            -22.98283\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.3262,\n" +
                    "                            -22.96481\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.33427,\n" +
                    "                            -22.96402\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.33616,\n" +
                    "                            -22.96829\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.342,\n" +
                    "                            -22.98157\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.34817,\n" +
                    "                            -22.97967\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.35142,\n" +
                    "                            -22.98062\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.3573,\n" +
                    "                            -22.98084\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.36522,\n" +
                    "                            -22.98032\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.36696,\n" +
                    "                            -22.98422\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.36717,\n" +
                    "                            -22.98855\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.36636,\n" +
                    "                            -22.99351\n" +
                    "                        ],\n" +
                    "                        [\n" +
                    "                            -43.36556,\n" +
                    "                            -22.99669\n" +
                    "                        ]\n" +
                    "                    ]\n" +
                    "                ]\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"address\": {\n" +
                    "            \"type\": \"Point\",\n" +
                    "            \"coordinates\": [\n" +
                    "                -43.297337,\n" +
                    "                -23.013538\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    }\n" +
                    "]")
            @RequestBody String partnerJson
    ) {
        try {
            List<Partner> partners = partnerService.createOrUpdatePartner(partnerJson);
            return new ResponseEntity<>(partners, HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable("id") UUID id) throws ExceptionError {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }
}
