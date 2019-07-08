package com.partner.api.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.partner.api.domain.dto.AddressDTO;
import lombok.*;
import org.springframework.data.annotation.Id;

import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
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

	public void convertAddress(AddressDTO address) {
		this.address = new GeoJsonPoint(address.getCoordinates()[0], address.getCoordinates()[1]);
	}

//	@GraphQLQuery(name = "coverageArea")
//	private GeoJsonMultiPolygon coverageArea;
}
