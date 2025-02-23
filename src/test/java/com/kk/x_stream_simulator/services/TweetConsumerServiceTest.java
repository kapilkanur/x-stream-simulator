package com.kk.x_stream_simulator.services;

import com.kk.x_stream_simulator.models.Tweet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

class TweetConsumerServiceTest {

    @Test
    void testConsumeTweet() {

        TweetConsumerService consumerService = new TweetConsumerService();
        Tweet tweet = new Tweet("1", "user123", "Hello, Kafka!", System.currentTimeMillis(), List.of("programming"));

        TweetConsumerService spyConsumer = Mockito.spy(consumerService);

        spyConsumer.consumeTweet(tweet);
        verify(spyConsumer).consumeTweet(tweet);
    }
}
