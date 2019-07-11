package com.partner.api.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.partner.api.domain.dto.AddressDTO;
import com.partner.api.domain.dto.CoverageAreaDTO;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("pdv")
public class Partner {

    @Id
    @GraphQLQuery(name = "id")
    private String id;

    @GraphQLQuery(name = "tradingName")
    private String tradingName;

    @GraphQLQuery(name = "ownerName")
    private String ownerName;

    @GraphQLQuery(name = "document")
    private String document;

    @GraphQLQuery(name = "address")
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint address;

    @GraphQLQuery(name = "coverageArea")
    private GeoJsonMultiPolygon coverageArea;

    public void convertAddress(AddressDTO address) {
        this.address = new GeoJsonPoint(address.getCoordinates()[0], address.getCoordinates()[1]);
    }

    public void convertCoverageArea(CoverageAreaDTO coverageAreaDTO) {

        List<GeoJsonPolygon> geoJsonPolygons = coverageAreaDTO.getCoordinates().stream().map(geoJsonPolygon -> {
            return geoJsonPolygon.stream().map(geoJsonLineString -> {
                List<Point> points = geoJsonLineString.stream().map(point -> new Point(point[0], point[1])).collect(Collectors.toList());
                return new GeoJsonPolygon(points);
            }).collect(Collectors.toList());
        }).flatMap(List::stream).collect(Collectors.toList());

        this.coverageArea = new GeoJsonMultiPolygon(geoJsonPolygons);
    }
}
