package com.iv.demo2.kafka.stream.stream.rating;

import java.util.Map;
import java.util.TreeMap;

public class FeedbackRatingTwoStoreValue {

    private Map<Integer, Long> ratingMap = new TreeMap<>();

    public Map<Integer, Long> getRatingMap() {
        return ratingMap;
    }

    public void setRatingMap(Map<Integer, Long> ratingMap) {
        this.ratingMap = ratingMap;
    }

    @Override
    public String toString() {
        return "FeedbackRatingTwoStoreValue [ratingMap=" + ratingMap + "]";
    }

}
