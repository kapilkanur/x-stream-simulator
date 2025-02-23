package com.kk.x_stream_simulator.services;

import com.kk.x_stream_simulator.models.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TweetProducerService {

    private final KafkaTemplate<String, Tweet> kafkaTemplate;

    public TweetProducerService(KafkaTemplate<String, Tweet> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTweet(Tweet tweet) {
        log.info("Sending tweet: {}", tweet);
        CompletableFuture<SendResult<String, Tweet>> future =
                kafkaTemplate.send("tweet-stream", tweet.getId(), tweet);

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
