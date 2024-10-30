package com.techbank.cqrs_core.infrastructure;

import com.techbank.cqrs_core.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> findByAggregateId(String aggregateId);
}
