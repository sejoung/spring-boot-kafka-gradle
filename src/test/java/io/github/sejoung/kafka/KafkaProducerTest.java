package io.github.sejoung.kafka;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.ExecutionException;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class KafkaProducerTest {
    @Autowired
    private KafkaProducer producer;

    @Test
    void sendTest() throws ExecutionException, InterruptedException {
        var topicName = "test";
        var future = producer.send(topicName, "test payload");
        Assertions.assertThat(future.get().getRecordMetadata().topic()).isEqualTo(topicName);
    }
}