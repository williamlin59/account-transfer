package com.demo.account.controller;

import com.demo.account.controller.resource.TransactionResource;
import com.demo.account.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Sample json looks like this:
 * [
 * {
 * "sourceAccountId" :1,
 * "targetAccountId" :2,
 * "amount" :100.00,
 * "transactionTime":"2013-12-18T14:30:40.010"
 * },
 * {
 * "sourceAccountId" :6,
 * "targetAccountId" :7,
 * "amount" :400.00,
 * "transactionTime":"2014-12-18T14:30:40.010"
 * }
 * ]
 * The end point is async so only 202 will be return to client.
 */
@RestController
@AllArgsConstructor(onConstructor = @__(@Inject))
public class TransactionController {
    private TransactionService transactionService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<HttpStatus> multipleTransactions(@RequestBody List<TransactionResource> transactionResources) {
        transactionService.solveTransactions(transactionResources);
        return ResponseEntity.accepted().build();
    }
}
