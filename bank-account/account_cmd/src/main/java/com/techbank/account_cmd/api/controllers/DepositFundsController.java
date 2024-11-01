package com.techbank.account_cmd.api.controllers;


import com.techbank.account_cmd.api.commands.DepositFundsCommand;
import com.techbank.account_cmd.api.dto.OpenAccountResponse;
import com.techbank.account_commom.dto.BaseResponse;
import com.techbank.cqrs_core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {

    private final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@RequestBody DepositFundsCommand command, @PathVariable(value = "id") String id) {
        try{
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds deposited successfully"), HttpStatus.OK);

        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.warning("Error depositing funds: " + e.getMessage());
            return ResponseEntity.badRequest().body(new BaseResponse("Error depositing funds"));
        } catch (Exception e) {
            logger.severe("|Error depositing funds: " + e.getMessage());
            return ResponseEntity.internalServerError().body(new BaseResponse("Error depositing funds"));
        }
    }
}
