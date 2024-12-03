package com.ipostu.demo16.springboot.docker.layers.web.mapper;

import com.ipostu.demo16.springboot.docker.layers.domain.Beer;
import com.ipostu.demo16.springboot.docker.layers.web.model.BeerDto;

import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventory(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);
}
