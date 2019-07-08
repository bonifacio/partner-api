package com.partner.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.partner.api.domain.Partner;

public interface PartnerRepository extends MongoRepository<Partner, String> {

}
