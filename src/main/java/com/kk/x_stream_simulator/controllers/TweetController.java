package com.kk.x_stream_simulator.controllers;

import com.kk.x_stream_simulator.models.Tweet;
import com.kk.x_stream_simulator.services.TweetProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetProducerService tweetProducerService;

    public TweetController(TweetProducerService tweetProducerService) {
        this.tweetProducerService = tweetProducerService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishTweet(@RequestBody Tweet tweet) {

        tweet.setId(UUID.randomUUID().toString());
        tweet.setTimestamp(Instant.now().toEpochMilli());

        tweetProducerService.sendTweet(tweet);
        return ResponseEntity.ok("Tweet published successfully!");
    }
}

