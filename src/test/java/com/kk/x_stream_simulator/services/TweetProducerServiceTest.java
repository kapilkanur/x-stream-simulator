package com.kk.x_stream_simulator.services;

import com.kk.x_stream_simulator.models.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class TweetProducerServiceTest {

    @Mock
    private  KafkaTemplate<String, Tweet> kafkaTemplate;
    private  TweetProducerService producerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producerService = new TweetProducerService(kafkaTemplate);
    }

    @Test
    void testSendTweet() {
        Tweet tweet = new Tweet("1", "user123", "Hello, Kafka!", System.currentTimeMillis());
        producerService.sendTweet(tweet);
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Tweet> tweetCaptor = ArgumentCaptor.forClass(Tweet.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), tweetCaptor.capture());

        assertEquals("tweets", topicCaptor.getValue());
        assertEquals(tweet.getId(), keyCaptor.getValue());
        assertEquals(tweet, tweetCaptor.getValue());
    }
}
