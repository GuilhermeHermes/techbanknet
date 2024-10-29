package com.techbank.cqrs_core.infrastructure;

import com.techbank.cqrs_core.commands.BaseCommand;
import com.techbank.cqrs_core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
