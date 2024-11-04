package com.techbank.account_query.infrastructure.consumers;

import com.techbank.account_commom.events.AccountClosedEvent;
import com.techbank.account_commom.events.AccountOpenedEvent;
import com.techbank.account_commom.events.FundsDepositedEvent;
import com.techbank.account_commom.events.FundsWithdrawnEvent;
import com.techbank.cqrs_core.events.BaseEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload BaseEvent event, Acknowledgment ack);
}
