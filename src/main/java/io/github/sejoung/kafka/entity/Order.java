package io.github.sejoung.kafka.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.AbstractAggregateRoot;

import io.github.sejoung.kafka.event.OrderPayedEvent;
import lombok.Getter;

@Entity
@Table(name = "ORDERS")
@Getter
public class Order extends AbstractAggregateRoot<Order> {

	public enum OrderStatus {ORDERED, PAYED, DELIVERED}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long id;

	@Column(name = "USER_ID")
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private OrderStatus orderStatus;

	protected Order() {
	}

	public Order(Long userId) {
		this.userId = userId;
		ordered();
	}

	private void ordered() {
		this.orderStatus = OrderStatus.ORDERED;
	}

	public void payed() {
		this.orderStatus = OrderStatus.PAYED;
		registerEvent(new OrderPayedEvent(Order.class, this));
	}
}
