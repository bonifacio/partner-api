package com.partner.api.domain.dto;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverageAreaDTO {

    @GraphQLQuery(name = "type")
    private String type;

    @GraphQLQuery(name = "coordinates")
    private ArrayList<ArrayList<ArrayList<double[]>>> coordinates;
}
