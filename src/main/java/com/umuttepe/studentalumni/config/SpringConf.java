package com.umuttepe.studentalumni.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConf {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
