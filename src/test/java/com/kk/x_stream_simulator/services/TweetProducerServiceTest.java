package com.kk.x_stream_simulator.services;

import com.kk.x_stream_simulator.models.Tweet;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TweetProducerServiceTest {

    @Mock
    private KafkaTemplate<String, Tweet> kafkaTemplate;

    @Mock
    private TweetStreamService tweetStreamService;

    private TweetProducerService producerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producerService = new TweetProducerService(kafkaTemplate, tweetStreamService);
    }

    @Test
    void testSendTweet() {
        Tweet tweet = new Tweet("1", "user123", "Hello, Kafka!", System.currentTimeMillis(), List.of("programming"));

        SendResult<String, Tweet> mockSendResult = mock(SendResult.class);
        TopicPartition topicPartition = new TopicPartition("tweets", 0);
        RecordMetadata metadata = new RecordMetadata(topicPartition, 0, 0, System.currentTimeMillis(), 0, 0);
        when(mockSendResult.getRecordMetadata()).thenReturn(metadata);

        CompletableFuture<SendResult<String, Tweet>> future = CompletableFuture.completedFuture(mockSendResult);
        when(kafkaTemplate.send(anyString(), anyString(), any(Tweet.class))).thenReturn(future);

        producerService.sendTweet(tweet);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Tweet> tweetCaptor = ArgumentCaptor.forClass(Tweet.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), tweetCaptor.capture());

        assertEquals("tweets", topicCaptor.getValue());
        assertEquals(tweet.getId(), keyCaptor.getValue());
        assertEquals(tweet, tweetCaptor.getValue());

        verifyNoMoreInteractions(kafkaTemplate);
    }
}
