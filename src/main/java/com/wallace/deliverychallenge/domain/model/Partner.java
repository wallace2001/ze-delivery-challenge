package com.wallace.deliverychallenge.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wallace.deliverychallenge.application.response.data.GeometryDeserializer;
import com.wallace.deliverychallenge.application.response.data.GeometrySerializer;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Table(name = "partner")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Partner {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "trading_name")
    private String tradingName;

    @Column(name = "owner_name")
    private String ownerName;

    private String document;

    @JsonDeserialize(using = GeometryDeserializer.class)
    @JsonSerialize(using = GeometrySerializer.class)
    @Column(columnDefinition = "geometry(MultiPolygon, 4326)")
    private Geometry coverageArea;

    @JsonDeserialize(using = GeometryDeserializer.class)
    @JsonSerialize(using = GeometrySerializer.class)
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point address;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Geometry getCoverageArea() {
        return coverageArea;
    }

    public void setCoverageArea(Geometry coverageArea) {
        this.coverageArea = coverageArea;
    }

    public Point getAddress() {
        return address;
    }

    public void setAddress(Point address) {
        this.address = address;
    }
}
