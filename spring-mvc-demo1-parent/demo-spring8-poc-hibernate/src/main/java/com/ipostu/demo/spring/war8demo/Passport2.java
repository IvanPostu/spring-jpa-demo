package com.ipostu.demo.spring.war8demo;

import jakarta.persistence.*;


@Entity
@Table(name = "passport2")
public class Passport2 {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Actor actor;

    @Column(name = "passport_number")
    private int passportNumber;

    public Passport2() {
    }

    public Passport2(Actor actor, int passportNumber) {
        this.actor = actor;
        this.passportNumber = passportNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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