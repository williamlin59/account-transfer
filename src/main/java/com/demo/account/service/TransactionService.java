package com.demo.account.service;

import com.demo.account.controller.resource.TransactionResource;

import java.util.List;

public interface TransactionService {

    void solveTransactions(List<TransactionResource> transactionResource);

}
