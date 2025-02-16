package com.ipostu.demo.spring.war8demo;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "passport")
public class Passport implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Actor actor;

    @Column(name = "passport_number")
    private int passportNumber;

    public Passport() {
    }

    public Passport(Actor actor, int passportNumber) {
        this.actor = actor;
        this.passportNumber = passportNumber;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }
}