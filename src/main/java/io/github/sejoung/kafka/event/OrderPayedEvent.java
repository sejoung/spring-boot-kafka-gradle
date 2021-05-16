package io.github.sejoung.kafka.event;

import org.springframework.context.ApplicationEvent;

import io.github.sejoung.kafka.entity.Order;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderPayedEvent extends ApplicationEvent {
	private final Order order;
	public OrderPayedEvent(Object source, Order order) {
		super(source);
		this.order = order;
	}

}
