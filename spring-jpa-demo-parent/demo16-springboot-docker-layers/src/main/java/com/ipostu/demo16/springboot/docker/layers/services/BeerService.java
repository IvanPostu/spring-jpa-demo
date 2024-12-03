package com.ipostu.demo16.springboot.docker.layers.services;

import com.ipostu.demo16.springboot.docker.layers.web.model.BeerDto;
import com.ipostu.demo16.springboot.docker.layers.web.model.BeerPagedList;
import com.ipostu.demo16.springboot.docker.layers.web.model.BeerStyleEnum;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
                            Boolean showInventoryOnHand);

    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerDto getByUpc(String upc);

    void deleteBeerById(UUID beerId);
}
