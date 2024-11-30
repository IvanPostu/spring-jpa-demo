package com.ipostu.demo11.inheritance.domain.joined;

import jakarta.persistence.Entity;

@Entity
public class ElectricGuitar extends Guitar{

    private Integer numberOfPickups;

    public Integer getNumberOfPickups() {
        return numberOfPickups;
    }

    public void setNumberOfPickups(Integer numberOfPickups) {
        this.numberOfPickups = numberOfPickups;
    }
}
