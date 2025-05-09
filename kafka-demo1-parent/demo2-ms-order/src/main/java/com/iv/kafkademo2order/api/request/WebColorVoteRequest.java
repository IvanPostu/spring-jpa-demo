package com.iv.kafkademo2order.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class WebColorVoteRequest {

    private String color;

    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime voteDateTime;

    public String getColor() {
        return color;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getVoteDateTime() {
        return voteDateTime;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setVoteDateTime(LocalDateTime voteDateTime) {
        this.voteDateTime = voteDateTime;
    }

    @Override
    public String toString() {
        return "WebColorVoteRequest [color=" + color + ", voteDateTime=" + voteDateTime + ", username=" + username
                + "]";
    }

}
