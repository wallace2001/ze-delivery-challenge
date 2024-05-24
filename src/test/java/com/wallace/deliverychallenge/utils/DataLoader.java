package com.wallace.deliverychallenge.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallace.deliverychallenge.domain.model.Partner;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class DataLoader {
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public List<Partner> loadData() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new ClassPathResource("pdvs.json").getFile();

        return objectMapper.readValue(file, new TypeReference<List<Partner>>() {});
    }

    public String loadDataRaw() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new ClassPathResource("pdvs.json").getFile();
        List<Partner> partners = objectMapper.readValue(file, new TypeReference<List<Partner>>() {});

        return objectMapper.writeValueAsString(partners);
    }

    public String readJsonFileAsString() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("pdvs.json"));
        return new String(bytes);
    }
}
