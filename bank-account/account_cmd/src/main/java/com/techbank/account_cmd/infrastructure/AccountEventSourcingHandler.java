package com.techbank.account_cmd.infrastructure;

import com.techbank.account_cmd.domain.AccountAggregate;
import com.techbank.cqrs_core.domain.AggregateRoot;
import com.techbank.cqrs_core.events.BaseEvent;
import com.techbank.cqrs_core.handlers.EventSourcingHandler;
import com.techbank.cqrs_core.infrastructure.EventStore;
import com.techbank.cqrs_core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventProducer eventProducer;


    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChanges(), aggregate.getVersion());
        aggregate.markChangeAsCommited();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.findByAggregateId(id);
        if(events != null && !events.isEmpty()){
            aggregate.replayEvent(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
    return aggregate;
    }

    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var aggregate = getById(aggregateId);

            // Verifica se o agregado existe e está ativo
            if (aggregate == null || !aggregate.getActive()) {
                continue;
            }

            // Recupera eventos associados ao agregado
            var events = eventStore.findByAggregateId(aggregateId);

            for (var event : events) {
                try {
                    eventProducer.produce(event.getClass().getSimpleName(), event);
                    System.out.println("Event republished: " + event + " for aggregate ID: " + aggregateId);
                } catch (Exception e) {
                    System.err.println("Failed to republish event: " + event + ". Error: " + e.getMessage());

                }
            }
        }
    }
}
