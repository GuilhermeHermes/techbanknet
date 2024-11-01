package com.techbank.cqrs_core.queries;


import com.techbank.cqrs_core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T command);
}
