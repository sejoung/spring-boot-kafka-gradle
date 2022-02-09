package io.github.sejoung.kafka.dto;

import java.beans.ConstructorProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class CustomMessage {
	private final Long messageId;
	private final String data;

	@ConstructorProperties({"messageId", "data"})
	@Builder
	private CustomMessage(Long messageId, String data) {
		this.messageId = messageId;
		this.data = data;
	}
}
