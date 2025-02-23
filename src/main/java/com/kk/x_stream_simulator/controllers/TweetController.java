package com.kk.x_stream_simulator.controllers;

import com.kk.x_stream_simulator.models.Tweet;
import com.kk.x_stream_simulator.services.TweetProducerService;
import com.kk.x_stream_simulator.services.TweetStreamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetProducerService tweetProducerService;
    private final TweetStreamService tweetStreamService;

    public TweetController(TweetProducerService tweetProducerService,
                           TweetStreamService tweetStreamService) {
        this.tweetProducerService = tweetProducerService;
        this.tweetStreamService = tweetStreamService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishTweet(@RequestBody Tweet tweet) {

        tweet.setId(UUID.randomUUID().toString());
        tweet.setTimestamp(Instant.now().toEpochMilli());

        tweetProducerService.sendTweet(tweet);
        return ResponseEntity.ok("Tweet published successfully!");
    }

    @GetMapping("/stream-tweets")
    public SseEmitter streamTweets() {
        return tweetStreamService.subscribeToStream();
    }
}

