package com.techbank.account_cmd.infrastructure;

import com.techbank.account_cmd.domain.AccountAggregate;
import com.techbank.cqrs_core.domain.AggregateRoot;
import com.techbank.cqrs_core.handlers.EventSourcingHandler;
import com.techbank.cqrs_core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;



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
}
