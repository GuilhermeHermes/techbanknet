package com.techbank.account_cmd.domain;

import com.techbank.account_cmd.api.commands.DepositFundsCommand;
import com.techbank.account_cmd.api.commands.OpenAccountCommand;
import com.techbank.account_cmd.api.commands.WithdrawFundsCommand;
import com.techbank.account_commom.events.AccountClosedEvent;
import com.techbank.account_commom.events.AccountOpenedEvent;
import com.techbank.account_commom.events.FundsDepositedEvent;
import com.techbank.account_commom.events.FundsWithdrawnEvent;
import com.techbank.cqrs_core.domain.AggregateRoot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

 @NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .createdDate(new Date())
                .build());
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount){
        if(!this.active){
            throw new IllegalStateException("Account is not active");
        }
        if(amount <= 0.0){
            throw new IllegalStateException("Amount should be greater than 0");
        }
        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void whitdrawFunds(double amount){
        if(this.balance < amount){
            throw new IllegalStateException("Insufficient funds");
        }else if(amount<= 0.0){
            throw new IllegalStateException("Amount should be greater than 0");
        }else if(!this.active){
            throw new IllegalStateException("Account is not active");
        }else if(this.balance - amount < 0){
            throw new IllegalStateException("Insufficient funds");
        }
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }


    public void closeAccount(){
        if(!this.active){
            throw new IllegalStateException("Account is not active");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }


    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }

    public double getBalance() {
        return balance;
    }

     public Boolean getActive() {
         return active;
     }
 }
