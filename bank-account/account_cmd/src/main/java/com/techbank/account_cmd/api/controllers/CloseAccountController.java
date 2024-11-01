package com.techbank.account_cmd.api.controllers;


import com.techbank.account_cmd.api.commands.CloseAccountCommand;
import com.techbank.account_cmd.api.dto.OpenAccountResponse;
import com.techbank.account_commom.dto.BaseResponse;
import com.techbank.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/closeBankAccount")
public class CloseAccountController {

private final Logger logger = Logger.getLogger(CloseAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable String id) {
        try {

            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Bank account closure request successfully completed"), HttpStatus.NO_CONTENT);

        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Error closing account: {0}", e.getMessage()));
            return new ResponseEntity<>(new BaseResponse("Error opening account"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMEssage = MessageFormat.format("Error while processing requeste to close bank account with id : {0}", id);
            logger.log(Level.SEVERE, safeErrorMEssage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMEssage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
