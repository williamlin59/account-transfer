package com.demo.account.util;

import com.demo.account.domain.TransactionEntity;
import com.demo.account.service.LocalCacheService;

import java.util.function.Predicate;

/**
 * Predicate used for determine transaction eligibility.
 * Currently check
 * 1. Source Account contains enough fund for tranfer
 * 2. Both source and target account exisits
 * 3. transacionTime and amount not null.
 */
public class PredicateUtil {

    public static Predicate<TransactionEntity> getTransactionEntityPredicate(LocalCacheService localCacheService) {
        return transactionEntity -> localCacheService.isAccountExist(transactionEntity.getSourceAccountId())
                && localCacheService.isAccountExist(transactionEntity.getTargetAccountId())
                && transactionEntity.getTransactionTime() != null
                && transactionEntity.getAmount() != null
                && localCacheService.getAmount(transactionEntity.getSourceAccountId()).compareTo(transactionEntity.getAmount()) > 0;
    }
}
