package io.github.sejoung.kafka;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ApplicationTest {

    @Value("${topic.single}")
    private String singleTopic;

    @Value("${topic.list}")
    private String listTopic;

    @Test
    void loaderTest() {

        log.debug("singleTopic = {}, listTopic = {}", singleTopic, listTopic);

        Assertions.assertThat(singleTopic).isEqualTo("test-topic");

        Assertions.assertThat(listTopic).isEqualTo("test-list-topic");
    }
}