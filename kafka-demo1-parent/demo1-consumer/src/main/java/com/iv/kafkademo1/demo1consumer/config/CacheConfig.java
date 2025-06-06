package com.iv.kafkademo1.demo1consumer.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.iv.kafkademo1.demo1consumer.consumer.PaymentRequestCacheKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean(name = "cachePurchaseRequest")
    public Cache<Integer, Boolean> cachePurchaseRequest() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(2))
                .maximumSize(1000)
                .build();
    }

    @Bean(name = "cachePurchaseRequest2")
    public Cache<String, Boolean> cachePurchaseRequest2() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(2))
                .maximumSize(1000)
                .build();
    }

    @Bean(name = "cachePaymentRequest")
    public Cache<PaymentRequestCacheKey, Boolean> cachePaymentRequest() {
        return Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(2)).maximumSize(1000).build();
    }

}
