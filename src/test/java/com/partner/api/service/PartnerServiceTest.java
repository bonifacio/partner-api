package com.partner.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.partner.api.domain.Partner;
import com.partner.api.domain.dto.PartnerDTO;
import com.partner.api.exception.NotFoundException;
import com.partner.api.repository.PartnerRepository;

@RunWith(MockitoJUnitRunner.class)
public class PartnerServiceTest {

	@Mock
	private PartnerRepository partnerRepository;
	
	@Mock
	private ModelMapper mapper;
	
	@InjectMocks
	private PartnerService partnerService;
	
	@Test
	public void shouldReturnOnePdv_whenGetPdvById() {
		
		var partner = Partner.builder().id("XYZ").build();
		when(partnerRepository.findById(partner.getId())).thenReturn(Optional.of(partner));
		when(mapper.map(partner, PartnerDTO.class)).thenReturn(PartnerDTO.builder().id(partner.getId()).build());
		var partnerReturned = partnerService.findById(partner.getId());
		
		assertNotNull(partnerReturned);
		assertEquals(partner.getId(), partnerReturned.getId());
	}

	@Test(expected = NotFoundException.class)
	public void shouldThrowNotFoundException_whenGetPdvById() {
		
		when(partnerRepository.findById("XYZ")).thenReturn(Optional.empty());
		partnerService.findById("XYZ");
	}
}
