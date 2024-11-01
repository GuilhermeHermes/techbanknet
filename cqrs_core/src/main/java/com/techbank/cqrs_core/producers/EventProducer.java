package com.techbank.cqrs_core.producers;

import com.techbank.cqrs_core.events.BaseEvent;

public interface EventProducer {
    void producer(String topic, BaseEvent event);
}
