package com.kk.x_stream_simulator.services;

import com.kk.x_stream_simulator.models.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TweetProducerService {

    private final KafkaTemplate<String, Tweet> kafkaTemplate;
    private final TweetStreamService tweetStreamService;

    public TweetProducerService(KafkaTemplate<String, Tweet> kafkaTemplate, TweetStreamService tweetStreamService) {
        this.kafkaTemplate = kafkaTemplate;
        this.tweetStreamService = tweetStreamService;
    }

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void sendTweet(Tweet tweet) {
        log.info("Sending tweet: {}", tweet);
        tweetStreamService.sendTweetToSubscribers(tweet);
        CompletableFuture<SendResult<String, Tweet>> future =
                kafkaTemplate.send("tweets", tweet.getId(), tweet);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send tweet: {} due to {}", tweet, ex.getMessage());
            } else {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Tweet sent successfully to topic {} at partition {} with offset {}",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }
        });
    }
}
