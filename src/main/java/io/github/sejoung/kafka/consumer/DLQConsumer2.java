package io.github.sejoung.kafka.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

import io.github.sejoung.kafka.dto.CustomMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DLQConsumer2 {

	@Bean
	public SeekToCurrentErrorHandler errorHandler(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
		return new SeekToCurrentErrorHandler(deadLetterPublishingRecoverer);
	}

	@Bean
	public DeadLetterPublishingRecoverer publisher(KafkaOperations<String, Object> operations) {
		return new DeadLetterPublishingRecoverer(operations);
	}

	@KafkaListener(id = "test-dlq2", topics = "custom-message-topic")
	public void listen(CustomMessage value) {
		log.info("listen {}", value);
	}

}
