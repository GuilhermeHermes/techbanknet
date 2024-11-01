package com.techbank.account_query.infrastructure;

import com.techbank.cqrs_core.commands.BaseCommand;
import com.techbank.cqrs_core.commands.CommandHandlerMethod;
import com.techbank.cqrs_core.domain.BaseEntity;
import com.techbank.cqrs_core.queries.BaseQuery;
import com.techbank.cqrs_core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class AccountQueryDispatcher implements QueryDispatcher{

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();


    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if(handlers == null || handlers.size() <= 0){
            throw new RuntimeException("No handler for " + query.getClass());
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("cannot send query to more than one handler!");
        }
        return handlers.get(0).handle(query);
    }
}
