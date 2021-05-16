package io.github.sejoung.kafka.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.github.sejoung.kafka.entity.Order;
import io.github.sejoung.kafka.repository.OrderRepository;

@AutoConfigureMockMvc
@SpringBootTest
class OrderRestControllerTest {
	private static final String BASE_URL = "/api/order/";
	private static final String DETAIL_URL = BASE_URL + "{orderId}";
	private static final String PAYED_URL = DETAIL_URL + "/payed";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OrderRepository repository;

	@Transactional
	@Test
	void create() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)).andDo(print()).andExpect(status().isOk());
	}

	@Transactional
	@Test
	void read() throws Exception {
		var order = repository.save(new Order(2L));
		this.mockMvc.perform(MockMvcRequestBuilders.get(DETAIL_URL, order.getId()))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Transactional
	@Test
	void payed() throws Exception {
		var order = repository.save(new Order(3L));
		this.mockMvc.perform(MockMvcRequestBuilders.patch(PAYED_URL, order.getId()))
			.andDo(print())
			.andExpect(status().isOk());

		TestTransaction.flagForCommit();
		TestTransaction.end();
	}

}