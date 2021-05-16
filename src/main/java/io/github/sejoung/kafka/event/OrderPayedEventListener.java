package io.github.sejoung.kafka.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPayedEventListener {
	private final ObjectMapper objectMapper;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${topic.event}")
	private String eventTopic;

	@TransactionalEventListener
	public void handleAfterCommit(OrderPayedEvent event) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(event);

		log.debug("OrderPayedEvent {}", json);
		kafkaTemplate.send(eventTopic, json);
	}
}
