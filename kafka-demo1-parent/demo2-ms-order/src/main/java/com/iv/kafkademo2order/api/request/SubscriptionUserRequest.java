package com.iv.kafkademo2order.api.request;

public class SubscriptionUserRequest {

    private String duration;

    private String username;

    public String getDuration() {
        return duration;
    }

    public String getUsername() {
        return username;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

