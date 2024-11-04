package com.techbank.account_cmd.infrastructure;

import com.techbank.account_cmd.domain.AccountAggregate;
import com.techbank.account_cmd.domain.EventStoreRepository;
import com.techbank.cqrs_core.events.BaseEvent;
import com.techbank.cqrs_core.events.EventModel;
import com.techbank.cqrs_core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs_core.exceptions.ConcurrencyException;
import com.techbank.cqrs_core.infrastructure.EventStore;
import com.techbank.cqrs_core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventProducer eventProducer;

    EventStoreRepository eventStoreRepository = null;

    public AccountEventStore(EventStoreRepository eventStoreRepository) {
        this.eventStoreRepository = eventStoreRepository;
    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateId(aggregateId);
        if(expectedVersion != -1 && eventStream.get(eventStream.size() -1).getVersion() != expectedVersion) {
            throw new ConcurrencyException("Concurrency exception");
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .aggregateId(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .eventType(event.getClass().getName())
                    .eventData(event)
                    .version(version)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if(!persistedEvent.getId().isEmpty()){
               eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> findByAggregateId(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateId(aggregateId);
        System.out.println("eventStream: " + eventStream);
        if(eventStream == null || eventStream.isEmpty()){
        throw new AggregateNotFoundException("Incorrect id provided");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }


    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if(eventStream == null || eventStream.isEmpty()){
            throw new AggregateNotFoundException("No aggregates found");
        }
        return eventStream.stream().map(EventModel::getAggregateId).distinct().collect(Collectors.toList());
    }
}
