package io.github.sejoung.kafka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import io.github.sejoung.kafka.dto.OrderResponse;
import io.github.sejoung.kafka.entity.Order;

@Mapper
public interface OrderMapper {
	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

	OrderResponse toOrderResponse(Order order);

}
