package com.ipostu.demo11.inheritance.domain.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("truck")
public class Truck extends Vehicle{

    private Integer payload;

    public Integer getPayload() {
        return payload;
    }

    public void setPayload(Integer payload) {
        this.payload = payload;
    }
}
