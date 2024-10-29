package com.techbank.account_cmd.api.commands;

import com.techbank.cqrs_core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
