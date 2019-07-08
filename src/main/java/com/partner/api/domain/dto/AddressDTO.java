package com.partner.api.domain.dto;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @GraphQLQuery(name = "type")
    private String type;

    @GraphQLQuery(name = "coordinates")
    private double[] coordinates;
}
