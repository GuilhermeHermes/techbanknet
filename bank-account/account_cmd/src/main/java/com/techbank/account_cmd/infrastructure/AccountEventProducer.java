package com.techbank.account_cmd.infrastructure;

import com.techbank.cqrs_core.events.BaseEvent;
import com.techbank.cqrs_core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class AccountEventProducer implements EventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void producer(String topic, BaseEvent event) {
    this.kafkaTemplate.send(topic, event);
    }

}
