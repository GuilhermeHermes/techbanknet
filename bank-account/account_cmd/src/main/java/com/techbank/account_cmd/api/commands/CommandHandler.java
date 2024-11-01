package com.techbank.account_cmd.api.commands;

public interface CommandHandler {
    void handle(WithdrawFundsCommand command);
    void handle(DepositFundsCommand command);
    void handle(OpenAccountCommand command);
    void handle(CloseAccountCommand command);
}
