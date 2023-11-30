package io.ikka.demo.demokafkasb3.producer;

import io.ikka.avro.demo.ExampleObject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class DemoKafkaProducer {
    private final KafkaTemplate<Integer, ExampleObject> kafkaTemplate;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    @Value("${app.topic}")
    private String topicName;


    public void sendMessage(ExampleObject msg) {
        kafkaTemplate.send(topicName, atomicInteger.incrementAndGet(), msg);
    }

    @PostConstruct
    void init() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ExampleObject msg = new ExampleObject(20231130, 34.5, "string-" + System.nanoTime());
            log.info("Sending message {}", msg);
            sendMessage(msg);
        }, 5, 5, TimeUnit.SECONDS);

    }
}
