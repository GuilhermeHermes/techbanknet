package com.techbank.account_query.domain;

import com.techbank.account_commom.dto.AccountType;
import com.techbank.cqrs_core.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double balance;
}
