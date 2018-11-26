package com.demo.account.service.impl;

import com.demo.account.controller.resource.TransactionResource;
import com.demo.account.domain.TransactionEntity;
import com.demo.account.domain.TransactionErrorEntity;
import com.demo.account.repository.TransactionErrorRepository;
import com.demo.account.service.LocalCacheService;
import com.demo.account.service.TransactionService;
import com.demo.account.util.PredicateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service validate transaction eligibility.
 * If eligible then update amount to localCache,
 * else persist transaction data to transactionError table
 * It also sort all transactions in chronological order to apply
 * trade correctly.
 */
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionErrorRepository transactionErrorRepository;
    private final LocalCacheService localCacheService;
    private final Predicate<TransactionEntity> transactionPredicate;

    @Inject
    TransactionServiceImpl(
            LocalCacheService localCacheService,
            TransactionErrorRepository transactionErrorRepository) {
        this.localCacheService = localCacheService;
        this.transactionErrorRepository = transactionErrorRepository;
        transactionPredicate = PredicateUtil.getTransactionEntityPredicate(localCacheService);
    }

    @Async
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void solveTransactions(List<TransactionResource> transactionResources) {
        List<TransactionErrorEntity> transactionErrorEntities = updateLocalCache(convertTransactionResourceToTransaction(transactionResources));
        if (transactionErrorEntities != null && !transactionErrorEntities.isEmpty()) {
            transactionErrorRepository.saveAll(transactionErrorEntities);
            log.warn("Some transactions are invalid, persist to transaction error table : {}", transactionErrorEntities);
        }
    }

    private List<TransactionErrorEntity> updateLocalCache(List<TransactionEntity> transactionEntities) {
        List<TransactionErrorEntity> transactionErrorEntities = new ArrayList<>();
        for (TransactionEntity transactionEntity : transactionEntities) {
            if (transactionPredicate.test(transactionEntity)) {
                BigDecimal transferOutAmount = transactionEntity.getAmount().multiply(new BigDecimal(-1));
                localCacheService.updateAmount(transactionEntity.getSourceAccountId(), transferOutAmount);
                localCacheService.updateAmount(transactionEntity.getTargetAccountId(), transactionEntity.getAmount());
            } else {
                transactionErrorEntities.add(mapTransactionEntityToTransactionErrorEntity(transactionEntity));
            }
        }
        return transactionErrorEntities;
    }

    private TransactionErrorEntity mapTransactionEntityToTransactionErrorEntity(TransactionEntity transactionEntity) {
        TransactionErrorEntity transactionErrorEntity = new TransactionErrorEntity();
        transactionErrorEntity.setAmount(transactionEntity.getAmount());
        transactionErrorEntity.setSourceAccountId(transactionEntity.getSourceAccountId());
        transactionErrorEntity.setTargetAccountId(transactionEntity.getTargetAccountId());
        transactionErrorEntity.setTransactionTime(transactionEntity.getTransactionTime());
        return transactionErrorEntity;
    }

    private List<TransactionEntity> convertTransactionResourceToTransaction(List<TransactionResource> transactionResources) {
        return transactionResources.stream().map(transactionResource ->
                new TransactionEntity
                        (transactionResource.getSourceAccountId(),
                                transactionResource.getTargetAccountId(),
                                transactionResource.getAmount(),
                                transactionResource.getTransactionTime())).sorted().collect(Collectors.toList());
    }

}
