package com.partner.api.config;

import com.partner.api.domain.Partner;
import com.partner.api.domain.dto.PartnerDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Partner, PartnerDTO>() {
            @Override
            protected void configure() {
                map().convertAddress(source.getAddress());
            }
        });
        modelMapper.addMappings(new PropertyMap<PartnerDTO, Partner>() {
            @Override
            protected void configure() {
                map().convertAddress(source.getAddress());
            }
        });
        return modelMapper;
    }

}
