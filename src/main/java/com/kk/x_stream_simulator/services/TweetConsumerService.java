package com.kk.x_stream_simulator.services;

import com.kk.x_stream_simulator.models.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TweetConsumerService {

    @KafkaListener(topics = "tweets", groupId = "tweet-consumers")
    public void consumeTweet(Tweet tweet) {
        log.info("Received tweet: {}", tweet);
    }

}

