package io.github.sejoung.kafka.service;

import org.springframework.stereotype.Service;

import io.github.sejoung.kafka.dto.OrderResponse;
import io.github.sejoung.kafka.entity.Order;
import io.github.sejoung.kafka.mapper.OrderMapper;
import io.github.sejoung.kafka.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository repository;

	public OrderResponse create() {
		var order = new Order(1L);
		repository.save(order);
		return OrderMapper.INSTANCE.toOrderResponse(order);
	}

	public OrderResponse read(Long orderId) {
		var order = repository.findById(orderId).orElseThrow();
		return OrderMapper.INSTANCE.toOrderResponse(order);
	}

	public OrderResponse payed(Long orderId) {
		var order = repository.findById(orderId).orElseThrow();
		order.payed();
		repository.save(order);
		return OrderMapper.INSTANCE.toOrderResponse(order);
	}
}
