package io.github.sejoung.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.sejoung.kafka.dto.OrderResponse;
import io.github.sejoung.kafka.service.OrderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order/")
public class OrderRestController {

	private final OrderService service;

	@PutMapping("/")
	public ResponseEntity<OrderResponse> create() {
		return ResponseEntity.ok(service.create());
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> read(@PathVariable Long orderId) {
		return ResponseEntity.ok(service.read(orderId));
	}

	@PatchMapping("/{orderId}/payed")
	public ResponseEntity<OrderResponse> payed(@PathVariable Long orderId) {
		return ResponseEntity.ok(service.payed(orderId));
	}
}
