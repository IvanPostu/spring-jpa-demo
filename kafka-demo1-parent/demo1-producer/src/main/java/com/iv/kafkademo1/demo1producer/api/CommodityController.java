package com.iv.kafkademo1.demo1producer.api;

import com.iv.kafkademo1.demo1common.entity.Commodity;
import com.iv.kafkademo1.demo1producer.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commodity/v1")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @GetMapping(value = "/all")
    public List<Commodity> generateAllCommodities() {
        return commodityService.createDummyCommodities();
    }

}
