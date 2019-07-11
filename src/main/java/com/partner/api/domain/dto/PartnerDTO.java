package com.partner.api.domain.dto;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerDTO {

    @GraphQLQuery(name = "id")
    private String id;

    @GraphQLQuery(name = "tradingName")
    private String tradingName;

    @GraphQLQuery(name = "ownerName")
    private String ownerName;

    @GraphQLQuery(name = "document")
    private String document;

    @GraphQLQuery(name = "address")
    private AddressDTO address;

    private CoverageAreaDTO coverageArea;

    public void convertAddress(@Nullable GeoJsonPoint address) {

    	if (address == null) {
    		return;
    	}
        this.address = AddressDTO.builder()
                .type(address.getType())
                .coordinates(address.getCoordinates().stream().mapToDouble(Double::doubleValue).toArray())
                .build();
    }

    public void convertCoverageArea(@Nullable GeoJsonMultiPolygon geoJsonMultiPolygon) {

        if (geoJsonMultiPolygon == null) {
            return;
        }
        var coordinates = geoJsonMultiPolygon.getCoordinates().stream().map(geoJsonPolygon -> {
            return geoJsonPolygon.getCoordinates().stream().map(geoJsonLineString -> {
                return geoJsonLineString.getCoordinates().stream().map(point -> {
                    return new double[]{point.getX(), point.getY()};
                }).collect(Collectors.toCollection((Supplier<ArrayList<double[]>>) ArrayList::new));
            }).collect(Collectors.toCollection((Supplier<ArrayList<ArrayList<double[]>>>) ArrayList::new));
        }).collect(Collectors.toList());

        this.coverageArea = CoverageAreaDTO.builder()
                .type(geoJsonMultiPolygon.getType())
                .coordinates(new ArrayList<ArrayList<ArrayList<double[]>>>(coordinates))
                .build();
    }
}

