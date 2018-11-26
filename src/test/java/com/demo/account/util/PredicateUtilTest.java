package com.demo.account.util;

import com.demo.account.domain.TransactionEntity;
import com.demo.account.service.LocalCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PredicateUtilTest {
    @Mock
    private LocalCacheService localCacheServiceMock;

    private Predicate<TransactionEntity> transactionEntityPredicate;

    @BeforeEach
    void setUp() {
        initMocks(this);
        transactionEntityPredicate = PredicateUtil.getTransactionEntityPredicate(localCacheServiceMock);
    }

    @Test
    void setTransactionEntityPredicateSuccess() {
        when(localCacheServiceMock.isAccountExist(1L)).thenReturn(true);
        when(localCacheServiceMock.isAccountExist(2L)).thenReturn(true);
        when(localCacheServiceMock.getAmount(1L)).thenReturn(new BigDecimal(3000));
        assertThat(transactionEntityPredicate.test(createTransactionEntity())).isTrue();
    }

    @Test
    void transactionEntityPredicateFailedAsSourceAccountNotExist() {
        when(localCacheServiceMock.isAccountExist(1L)).thenReturn(false);
        when(localCacheServiceMock.isAccountExist(2L)).thenReturn(true);
        when(localCacheServiceMock.getAmount(1L)).thenReturn(new BigDecimal(3000));
        assertThat(transactionEntityPredicate.test(createTransactionEntity())).isFalse();
    }

    @Test
    void transactionEntityPredicateFailedAsTargetAccountNotExist() {
        when(localCacheServiceMock.isAccountExist(1L)).thenReturn(true);
        when(localCacheServiceMock.isAccountExist(2L)).thenReturn(false);
        when(localCacheServiceMock.getAmount(1L)).thenReturn(new BigDecimal(3000));
        assertThat(transactionEntityPredicate.test(createTransactionEntity())).isFalse();
    }

    @Test
    void transactionEntityPredicateFailedAsNoSufficientFund() {
        when(localCacheServiceMock.isAccountExist(1L)).thenReturn(true);
        when(localCacheServiceMock.isAccountExist(2L)).thenReturn(true);
        when(localCacheServiceMock.getAmount(1L)).thenReturn(new BigDecimal(100));
        assertThat(transactionEntityPredicate.test(createTransactionEntity())).isFalse();
    }

    @Test
    void transactionEntityPredicateFailedAsNullTransactionTime() {
        when(localCacheServiceMock.isAccountExist(1L)).thenReturn(true);
        when(localCacheServiceMock.isAccountExist(2L)).thenReturn(true);
        when(localCacheServiceMock.getAmount(1L)).thenReturn(new BigDecimal(3000));
        TransactionEntity transactionEntity = createTransactionEntity();
        transactionEntity.setTransactionTime(null);
        assertThat(transactionEntityPredicate.test(transactionEntity)).isFalse();
    }

    @Test
    void transactionEntityPredicateFailedAsNullAmount() {
        when(localCacheServiceMock.isAccountExist(1L)).thenReturn(true);
        when(localCacheServiceMock.isAccountExist(2L)).thenReturn(true);
        when(localCacheServiceMock.getAmount(1L)).thenReturn(new BigDecimal(3000));
        TransactionEntity transactionEntity = createTransactionEntity();
        transactionEntity.setAmount(null);
        assertThat(transactionEntityPredicate.test(transactionEntity)).isFalse();
    }

    private TransactionEntity createTransactionEntity() {
        return new TransactionEntity(1L, 2L, new BigDecimal(200), DateTimeUtil.getCurrentLocalDateTime());
    }
}