package com.ipostu.demo.spring15;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class App {

    // https://reqres.in/
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> requestData = new HashMap<>();
        requestData.put("name", "Test name");
        requestData.put("job", "Test job");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestData);
        String url = "https://reqres.in/api/users";
        String response = restTemplate.postForObject(url, request, String.class);

        System.out.println(response);
    }

    private static void getUserAndPrintResponse(RestTemplate restTemplate) {
        String url = "https://reqres.in/api/users/2";
        String response = restTemplate.getForObject(url, String.class);

        System.out.println(response);
    }

}
