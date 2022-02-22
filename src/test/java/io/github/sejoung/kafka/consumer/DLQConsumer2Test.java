package io.github.sejoung.kafka.consumer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092",
	"auto.create.topics.enable=true"})
@Slf4j
class DLQConsumer2Test {
	@Autowired
	private KafkaTemplate<String, byte[]> kafkaTemplate;
	private static final String TOPIC = "custom-message-topic";

	private static final String TOPIC_DLQ = "custom-message-topic.DLQ";

	private static final String BROKERS = "localhost:9092";

	@Test
	void errorTest() {

		var message = "sejoung-test";
		var producerProps = KafkaTestUtils.producerProps(BROKERS);

		try (var producer = new KafkaProducer<Object, String>(producerProps)) {
			var record = new ProducerRecord<>(TOPIC, message);
			producer.send(record);
			producer.flush();
		}

		kafkaTemplate.send(TOPIC, message.getBytes(StandardCharsets.UTF_8));
		kafkaTemplate.flush();
		var consumerProps = KafkaTestUtils.consumerProps(BROKERS, "test", "false");
		consumerProps.put("auto.offset.reset", "earliest");
		try (var consumer = new KafkaConsumer<>(consumerProps)) {
			var i = 0;
			while (i == 0) {
				consumer.subscribe(Collections.singletonList(TOPIC_DLQ));
				var records = consumer.poll(Duration.ofMillis(1000));
				for (var record : records) {
					log.debug("record = {}", record.value());
					if (message.equals(record.value())) {
						i = 1;
					}
				}
				consumer.commitSync();
			}
		}
	}

}