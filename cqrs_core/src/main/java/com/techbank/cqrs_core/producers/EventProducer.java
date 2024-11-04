package com.techbank.cqrs_core.producers;

import com.techbank.cqrs_core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
