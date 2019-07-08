package com.partner.api.graphql;

import java.util.Optional;

import com.partner.api.domain.dto.PartnerDTO;
import io.leangen.graphql.annotations.GraphQLMutation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partner.api.domain.Partner;
import com.partner.api.service.PartnerService;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class PartnerApi {

	private PartnerService partnerService;

	@Autowired
	public PartnerApi(PartnerService partnerService) {
		this.partnerService = partnerService;
	}
	
	@GraphQLQuery(name = "pdv")
	public Optional<PartnerDTO> findById(@GraphQLArgument(name = "id") String id) {
		return partnerService.findById(id);
	}

	@GraphQLMutation(name = "createPdv")
	public PartnerDTO create(@GraphQLArgument(name = "partner") PartnerDTO partnerDTO) {
		return partnerService.create(partnerDTO);
	}
}
