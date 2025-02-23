package com.ipostu.demo.spring.jar17demo.controllers.rest;

public final class PersonNotFoundResponse {
    private final String message;
    private final long timestamp;

    public PersonNotFoundResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
