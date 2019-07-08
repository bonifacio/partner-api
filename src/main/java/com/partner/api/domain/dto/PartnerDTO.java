package com.partner.api.domain.dto;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
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

    public void convertAddress(GeoJsonPoint address) {

        this.address = AddressDTO.builder()
                .type(address.getType())
                .coordinates(address.getCoordinates().stream().mapToDouble(Double::doubleValue).toArray())
                .build();
    }
}

