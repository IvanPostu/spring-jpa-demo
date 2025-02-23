package com.ipostu.demo.spring.jar16demo.config;

import com.ipostu.demo.spring.jar16demo.controllers.rest.PersonRequest;
import com.ipostu.demo.spring.jar16demo.controllers.rest.PersonResponse;
import com.ipostu.demo.spring.jar16demo.models.Person;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<PersonRequest, Person> propertyMapper = modelMapper.createTypeMap(PersonRequest.class,
                Person.class);
        propertyMapper
                .addMappings(mapper -> mapper.skip(Person::setId));

        modelMapper.createTypeMap(Person.class, PersonResponse.class);

        return modelMapper;
    }

}
