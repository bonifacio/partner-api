package com.partner.api.service;

import java.util.Optional;

import com.partner.api.domain.dto.AddressDTO;
import com.partner.api.domain.dto.PartnerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.partner.api.domain.Partner;
import com.partner.api.repository.PartnerRepository;

@Service
@Transactional
public class PartnerService {

	private PartnerRepository partnerRepository;

	private ModelMapper modelMapper;

	@Autowired
	public PartnerService(PartnerRepository partnerRepository, ModelMapper modelMapper) {
		this.partnerRepository = partnerRepository;
		this.modelMapper = modelMapper;
	}
	
	public Optional<PartnerDTO> findById(String id) {
		return null;//partnerRepository.findById(id);
	}
	
	public PartnerDTO create(PartnerDTO partnerDTO) {

//		double[] coordinates = partnerDTO.getAddress().getCoordinates();
//		GeoJsonPoint address = new GeoJsonPoint(coordinates[0], coordinates[1]);
//		Partner partner = Partner.builder()
//				.document(partnerDTO.getDocument())
//				.ownerName(partnerDTO.getOwnerName())
//				.tradingName(partnerDTO.getTradingName())
//				.address(address)
//				.build();

		Partner partner = converToEntity(partnerDTO);
		return convertToDto(partnerRepository.save(partner));
	}

	private PartnerDTO convertToDto(Partner partner) {
		return modelMapper.map(partner, PartnerDTO.class);
	}

	private Partner converToEntity(PartnerDTO partnerDTO) {
		return modelMapper.map(partnerDTO, Partner.class);
	}
}
