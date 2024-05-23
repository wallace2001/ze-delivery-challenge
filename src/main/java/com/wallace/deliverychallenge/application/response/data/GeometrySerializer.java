package com.wallace.deliverychallenge.application.response.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;

public class GeometrySerializer extends JsonSerializer<Geometry> {

    @Override
    public void serialize(Geometry geometry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", geometry.getGeometryType());
        jsonGenerator.writeFieldName("coordinates");
        writeCoordinates(geometry, jsonGenerator);
        jsonGenerator.writeEndObject();
    }

    private void writeCoordinates(Geometry geometry, JsonGenerator jsonGenerator) throws IOException {
        if ("Point".equals(geometry.getGeometryType())) {
            writePointCoordinates(geometry, jsonGenerator);
        } else {
            writeMultiPolygonCoordinates(geometry, jsonGenerator);
        }
    }

    private void writePointCoordinates(Geometry geometry, JsonGenerator jsonGenerator) throws IOException {
        Coordinate coordinate = geometry.getCoordinate();
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(coordinate.getX());
        jsonGenerator.writeNumber(coordinate.getY());
        jsonGenerator.writeEndArray();
    }

    private void writeMultiPolygonCoordinates(Geometry geometry, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartArray(); // Inicia a escrita do array de polígonos
        for (int i = 0; i < geometry.getNumGeometries(); i++) {
            Geometry polygon = geometry.getGeometryN(i);
            jsonGenerator.writeStartArray(); // Inicia a escrita de um polígono
            writePolygonCoordinates(polygon, jsonGenerator); // Escreve as coordenadas do polígono
            jsonGenerator.writeEndArray(); // Encerra a escrita do polígono
        }
        jsonGenerator.writeEndArray(); // Encerra a escrita do array de polígonos
    }

    private void writePolygonCoordinates(Geometry geometry, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartArray(); // Inicia a escrita das coordenadas do polígono
        for (Coordinate coordinate : geometry.getCoordinates()) {
            jsonGenerator.writeStartArray(); // Inicia a escrita de uma coordenada [x, y]
            jsonGenerator.writeNumber(coordinate.getX()); // Escreve a coordenada x
            jsonGenerator.writeNumber(coordinate.getY()); // Escreve a coordenada y
            jsonGenerator.writeEndArray(); // Encerra a escrita da coordenada [x, y]
        }
        jsonGenerator.writeEndArray(); // Encerra a escrita das coordenadas do polígono
    }

}