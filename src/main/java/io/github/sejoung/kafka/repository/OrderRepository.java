package io.github.sejoung.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.sejoung.kafka.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
