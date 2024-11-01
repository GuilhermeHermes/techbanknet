package com.techbank.account_query.infrastructure.consumers;

import com.techbank.account_commom.events.AccountClosedEvent;
import com.techbank.account_commom.events.AccountOpenedEvent;
import com.techbank.account_commom.events.FundsDepositedEvent;
import com.techbank.account_commom.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consumer(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consumer(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consumer(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
    void consumer(@Payload AccountClosedEvent event, Acknowledgment ack);

}
