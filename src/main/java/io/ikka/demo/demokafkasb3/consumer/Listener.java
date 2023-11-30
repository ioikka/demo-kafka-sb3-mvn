package io.ikka.demo.demokafkasb3.consumer;

import io.ikka.avro.demo.ExampleObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Listener {
    @KafkaListener(topics = "${app.topic}", groupId = "${app.consumer.group.id}")
    public void listenGroupFoo(@Payload ExampleObject message) {
        log.info("Received Message in group foo: " + message);
    }
}
