package com.techbank.account_commom.events;

import com.techbank.account_commom.dto.AccountType;
import com.techbank.cqrs_core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
