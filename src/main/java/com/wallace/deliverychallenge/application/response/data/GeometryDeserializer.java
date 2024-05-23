package com.wallace.deliverychallenge.application.response.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.io.IOException;

public class GeometryDeserializer extends StdDeserializer<Geometry> {

    private final WKTReader wktReader = new WKTReader();

    public GeometryDeserializer() {
        this(null);
    }

    protected GeometryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Geometry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String wkt = convertToWKT(node);
        try {
            return wktReader.read(wkt);
        } catch (ParseException e) {
            throw new IOException("Failed to deserialize WKT: " + wkt, e);
        }
    }

    private String convertToWKT(JsonNode node) {
        String type = node.get("type").asText();
        JsonNode coordinates = node.get("coordinates");

        return switch (type) {
            case "MultiPolygon" -> convertMultiPolygonToWKT(coordinates) + "', 4326)";
            case "Point" -> convertPointToWKT(coordinates) + "', 4326)";
            default -> throw new IllegalArgumentException("Unsupported geometry type: " + type);
        };
    }

    private String convertMultiPolygonToWKT(JsonNode coordinates) {
        StringBuilder sb = new StringBuilder("MULTIPOLYGON (");
        for (int i = 0; i < coordinates.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(convertPolygonToWKT(coordinates.get(i)));
        }
        sb.append(")");
        return sb.toString();
    }

    private String convertPolygonToWKT(JsonNode polygon) {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < polygon.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("(");
            appendCoordinates(sb, polygon.get(i));
            sb.append(")");
        }
        sb.append(")");
        return sb.toString();
    }

    private String convertPointToWKT(JsonNode coordinates) {
        return "POINT (" + coordinates.get(0).asDouble() + " " + coordinates.get(1).asDouble() + ")";
    }

    private void appendCoordinates(StringBuilder sb, JsonNode coordinates) {
        for (int i = 0; i < coordinates.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(coordinates.get(i).get(0).asDouble()).append(" ").append(coordinates.get(i).get(1).asDouble());
        }
    }

}

