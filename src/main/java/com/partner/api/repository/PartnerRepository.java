package com.partner.api.repository;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.partner.api.domain.Partner;

import java.util.List;

public interface PartnerRepository extends MongoRepository<Partner, String> {

    List<Partner> findByAddressNear(Point point);
}
