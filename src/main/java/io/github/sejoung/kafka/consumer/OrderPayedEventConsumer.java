package io.github.sejoung.kafka.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderPayedEventConsumer {

	@KafkaListener(topics = "${topic.event}", groupId = "list")
	public void receive(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
		log.info("records size='{}'", records.size());
		records.forEach(consumerRecord -> System.out.println(consumerRecord.value()));
		ack.acknowledge();
	}
}
