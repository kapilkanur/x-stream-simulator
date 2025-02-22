package com.kk.x_stream_simulator.services;

import com.kk.x_stream_simulator.models.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TweetProducerService {

    private final KafkaTemplate<String, Tweet> kafkaTemplate;

    public TweetProducerService(KafkaTemplate<String, Tweet> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTweet(Tweet tweet) {
        log.info("Sending tweet: {}", tweet);
        kafkaTemplate.send("tweets", tweet.getId(), tweet);
    }
}
