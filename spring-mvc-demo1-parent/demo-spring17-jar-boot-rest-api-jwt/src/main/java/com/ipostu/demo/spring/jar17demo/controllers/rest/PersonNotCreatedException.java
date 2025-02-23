package com.ipostu.demo.spring.jar17demo.controllers.rest;

public class PersonNotCreatedException extends RuntimeException {

    public PersonNotCreatedException(String message) {
        super(message);
    }

}
