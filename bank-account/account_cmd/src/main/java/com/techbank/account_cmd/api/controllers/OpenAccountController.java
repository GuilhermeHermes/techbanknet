package com.techbank.account_cmd.api.controllers;


import com.techbank.account_cmd.api.commands.OpenAccountCommand;
import com.techbank.account_cmd.api.dto.OpenAccountResponse;
import com.techbank.account_commom.dto.BaseResponse;
import com.techbank.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {

private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Account opened successfully", id), HttpStatus.CREATED);

        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Error opening account: {0}", e.getMessage()));
            return new ResponseEntity<>(new BaseResponse("Error opening account"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMEssage = MessageFormat.format("Error opening account: {0}", e.getMessage());
            logger.log(Level.SEVERE, safeErrorMEssage, e);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMEssage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
