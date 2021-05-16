# spring-boot-kafka-gradle

## 카프카 설정후 테스트 

```
docker-compose -f docker-compose-kafka.yml up -d

docker-compose -f docker-compose-kafka.yml down

docker-compose -f docker-compose-kafka.yml ps

```

## 임베디드 카프카 사용 테스트

KafkaConsumer.java

```java

    @KafkaListener(topics = "${topic.single}", groupId = "single")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        this.runCountDown(consumerRecord);
    }

    //@KafkaListener(topics = "${topic.list}", groupId = "list")
    public void receive(List<ConsumerRecord<?, ?>> records) {
        log.info("records size='{}'", records.size());
        records.forEach(this::runCountDown);
    }

```

KafkaConsumerTest.java
```java
    @Test
    void singleReceive() throws InterruptedException {
            log.info(" count {} ", consumer.getLatch().getCount());
            consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
            log.info(" count {} ", consumer.getLatch().getCount());
            Assertions.assertThat(consumer.getPayload()).contains("single9");
            }

    @Disabled
    @Test
    void listReceive() throws InterruptedException {
            log.info(" count {} ", consumer.getLatch().getCount());
            consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
            log.info(" count {} ", consumer.getLatch().getCount());
            Assertions.assertThat(consumer.getPayload()).contains("list9");
            }

```

위에 @KafkaListener 가 주석이 되어 있는데 테스트를 위해서 application.yml 파일에 listener type 을 batch로 수정하고 위에 @KafkaListener 주석 처리 하면 배치에 대한 테스트를 진행 할수 있다.

KafkaConsumer.java
```java

    //@KafkaListener(topics = "${topic.single}", groupId = "single")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        this.runCountDown(consumerRecord);
    }

    @KafkaListener(topics = "${topic.list}", groupId = "list")
    public void receive(List<ConsumerRecord<?, ?>> records) {
        log.info("records size='{}'", records.size());
        records.forEach(this::runCountDown);
    }

```

KafkaConsumerTest.java
```java

    @Disabled
    @Test
    void singleReceive() throws InterruptedException {
            log.info(" count {} ", consumer.getLatch().getCount());
            consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
            log.info(" count {} ", consumer.getLatch().getCount());
            Assertions.assertThat(consumer.getPayload()).contains("single9");
    }


    @Test
    void listReceive() throws InterruptedException {
            log.info(" count {} ", consumer.getLatch().getCount());
            consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
            log.info(" count {} ", consumer.getLatch().getCount());
            Assertions.assertThat(consumer.getPayload()).contains("list9");
    }

```

# 참고
* [docker-kafka](https://github.com/sejoung/docker-kafka)
