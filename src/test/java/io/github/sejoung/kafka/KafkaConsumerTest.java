package io.github.sejoung.kafka;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class KafkaConsumerTest {
    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumer consumer;

    @Value("${topic.single}")
    private String singleTopic;

    @Value("${topic.list}")
    private String listTopic;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            producer.send(singleTopic, "single" + i);
            producer.send(listTopic, "list" + i);
        }
    }

    @Test
    void singleReceive() throws InterruptedException {
        log.info(" count {} ", consumer.getLatch().getCount());
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        log.info(" count {} ", consumer.getLatch().getCount());
        Assertions.assertThat(consumer.getPayload()).contains("single9");
    }

    @Disabled
    @Test
    void listReceive() throws InterruptedException {
        log.info(" count {} ", consumer.getLatch().getCount());
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        log.info(" count {} ", consumer.getLatch().getCount());
        Assertions.assertThat(consumer.getPayload()).contains("list9");
    }
}