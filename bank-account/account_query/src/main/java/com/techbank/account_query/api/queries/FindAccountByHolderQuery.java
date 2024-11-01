package com.techbank.account_query.api.queries;

import com.techbank.cqrs_core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByHolderQuery extends BaseQuery {
private String accountHolder;
}
