package com.techbank.account_query.infrastructure;

import com.techbank.cqrs_core.commands.BaseCommand;
import com.techbank.cqrs_core.commands.CommandHandlerMethod;
import com.techbank.cqrs_core.domain.BaseEntity;
import com.techbank.cqrs_core.queries.BaseQuery;
import com.techbank.cqrs_core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
