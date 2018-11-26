package com.demo.account.service.impl;

import com.demo.account.controller.resource.TransactionResource;
import com.demo.account.domain.LocalAccountCache;
import com.demo.account.domain.TransactionErrorEntity;
import com.demo.account.repository.AccountRepository;
import com.demo.account.repository.TransactionErrorRepository;
import com.demo.account.service.LocalCacheService;
import com.demo.account.service.TransactionService;
import com.demo.account.util.DateTimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Rollback
@Transactional
class TransactionServiceImplTest {
    private static final BigDecimal DEFAULT_FUND = new BigDecimal(100);
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LocalCacheService localCacheService;
    @Autowired
    private LocalAccountCache localAccountCache;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionErrorRepository transactionErrorRepository;

    @BeforeEach
    void tearUp() {
        transactionErrorRepository.deleteAllInBatch();
        accountRepository.deleteAll();
        accountRepository.flush();
    }

    @Test
    void solveTransactions() throws InterruptedException {
        localCacheService.initializeLocalCacheForTest();
        List<TransactionResource> resources = createTransactionResourceList();
        transactionService.solveTransactions(resources);
        Thread.sleep(1000);
        localAccountCache.getLocalCache().entrySet().stream().
                filter(entry -> entry.getKey() != 1 && entry.getKey() != 10).
                forEach(entry -> assertThat(entry.getValue()).isEqualTo(new BigDecimal(5000)));
        assertThat(localAccountCache.getAmount(1L)).isEqualTo(new BigDecimal(4900));
        assertThat(localAccountCache.getAmount(10L)).isEqualTo(new BigDecimal(5100));
        List<TransactionErrorEntity> transactionErrorEntities = transactionErrorRepository.findAll();
        assertThat(transactionErrorEntities.size()).isEqualTo(1);
        TransactionErrorEntity transactionErrorEntity = transactionErrorEntities.get(0);
        assertThat(transactionErrorEntity.getTargetAccountId()).isEqualTo(11);
        assertThat(transactionErrorEntity.getSourceAccountId()).isEqualTo(10);
        assertThat(transactionErrorEntity.getAmount()).isEqualTo(new BigDecimal(100));
    }

    private List<TransactionResource> createTransactionResourceList() {
        return LongStream.rangeClosed(1, 10).mapToObj(i -> createTransactionResource(i, i + 1, DEFAULT_FUND)).collect(Collectors.toList());
    }

    private TransactionResource createTransactionResource(Long sourceAccount, Long targetAccount, BigDecimal amount) {
        TransactionResource transactionResource = new TransactionResource();
        transactionResource.setAmount(amount);
        transactionResource.setSourceAccountId(sourceAccount);
        transactionResource.setTargetAccountId(targetAccount);
        transactionResource.setTransactionTime(DateTimeUtil.getCurrentLocalDateTime());
        return transactionResource;
    }
}