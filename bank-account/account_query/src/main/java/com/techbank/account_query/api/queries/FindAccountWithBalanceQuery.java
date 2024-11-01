package com.techbank.account_query.api.queries;

import com.techbank.account_query.api.dto.EqualityType;
import com.techbank.cqrs_core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
private EqualityType equalityType;
private double balance;
}
