package com.techbank.account_query.infrastructure.handlers;

import com.techbank.account_commom.events.AccountClosedEvent;
import com.techbank.account_commom.events.AccountOpenedEvent;
import com.techbank.account_commom.events.FundsDepositedEvent;
import com.techbank.account_commom.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);


}
