package com.iv.kafkademo2order.api.server;


import com.iv.kafkademo2order.api.request.FeedbackRequest;
import com.iv.kafkademo2order.command.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackApi {

    @Autowired
    private FeedbackService service;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody FeedbackRequest request) {
        service.createFeedback(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Thanks for your feedback");
    }
}

