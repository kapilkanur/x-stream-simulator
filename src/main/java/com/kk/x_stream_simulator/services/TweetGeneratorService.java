package com.kk.x_stream_simulator.services;

import com.kk.x_stream_simulator.models.Tweet;
import net.datafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TweetGeneratorService {

    private final TweetProducerService tweetProducerService;
    private final Faker faker = new Faker();
    private final List<String> categories = List.of(
            faker.programmingLanguage().name(),
            faker.hacker().noun(),
            faker.industrySegments().industry()
    );


    public TweetGeneratorService(TweetProducerService tweetProducerService) {
        this.tweetProducerService = tweetProducerService;
    }

    @Scheduled(fixedRate = 5000)
    public void generateRandomTweet() {
        Tweet tweet = new Tweet(
                UUID.randomUUID().toString(),
                faker.name().name(),
                faker.chuckNorris().fact(),
                Instant.now().toEpochMilli(),
                categories
        );

        log.info("Generated Tweet: {}", tweet);
        tweetProducerService.sendTweet(tweet);
    }
}
