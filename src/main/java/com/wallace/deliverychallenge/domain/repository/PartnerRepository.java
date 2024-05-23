package com.wallace.deliverychallenge.domain.repository;

import com.wallace.deliverychallenge.domain.model.Partner;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, UUID> {
    @Query(value = "SELECT p.*\n" +
            "FROM partner p\n" +
            "WHERE ST_DWithin(ST_Centroid(p.coverage_area), ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :maxDistance)\n" +
            "ORDER BY ST_Distance(ST_Centroid(p.coverage_area), ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326))\n" +
            "LIMIT 1;", nativeQuery = true)
    Partner findNearestPartner(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("maxDistance") double maxDistance);
}
