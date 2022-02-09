package io.github.sejoung.kafka.consumer;

import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import io.github.sejoung.kafka.dto.CustomMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DLQConsumer {

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CustomMessage> customMessageKafkaListenerContainerFactory(
		KafkaProperties kafkaProperties, KafkaOperations<String, Object> operations) {
		ConcurrentKafkaListenerContainerFactory<String, CustomMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(
			new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(),
				new ErrorHandlingDeserializer<>(new JsonDeserializer<>(CustomMessage.class))));

		factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(operations,
			(cr, e) -> new TopicPartition("history-5m-retry", cr.partition())), new FixedBackOff(1000L, 3)));

		return factory;
	}

	@KafkaListener(id = "test-dlq1", topics = "custom-message-topic", containerFactory = "customMessageKafkaListenerContainerFactory")
	public void listen(CustomMessage value) {
		log.info("listen {}", value);
	}

}
