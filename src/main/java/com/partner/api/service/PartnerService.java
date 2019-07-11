package com.partner.api.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.partner.api.domain.Partner;
import com.partner.api.domain.dto.PartnerDTO;
import com.partner.api.exception.NotFoundException;
import com.partner.api.repository.PartnerRepository;
import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

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

	@PostConstruct
	public void postConstruct() {

		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("partner");
		MongoCollection<Document> collection = db.getCollection("pdv");
		collection.createIndex(Indexes.geo2dsphere("address"));
	}
	
	public PartnerDTO findById(String id) {
		Partner partner = partnerRepository.findById(id).orElseThrow(() -> new NotFoundException(Partner.class.getSimpleName()));
		return convertToDto(partner);
	}

	public List<PartnerDTO> findByAddressNear(double lng, double lat) {
		return partnerRepository.findByAddressNear(new Point(lng, lat)).stream().map(partner -> convertToDto(partner)).collect(Collectors.toList());
	}

	public PartnerDTO create(PartnerDTO partnerDTO) {
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
