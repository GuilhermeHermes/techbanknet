package com.techbank.account_cmd.api.controllers;


import com.techbank.account_cmd.api.commands.DepositFundsCommand;
import com.techbank.account_cmd.api.commands.WithdrawFundsCommand;
import com.techbank.account_commom.dto.BaseResponse;
import com.techbank.cqrs_core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {

    private final Logger logger = Logger.getLogger(WithdrawFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@RequestBody WithdrawFundsCommand command, @PathVariable(value = "id") String id) {
        try{
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds withdraw successfully"), HttpStatus.OK);

        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.warning("Error withdrawing funds: " + e.getMessage());
            return ResponseEntity.badRequest().body(new BaseResponse("Error withdrawing funds, check the data provided"));
        } catch (Exception e) {
            logger.severe("|Error withdrawing funds: " + e.getMessage());
            return ResponseEntity.internalServerError().body(new BaseResponse("|Error withdrawing funds"));
        }
    }
}
