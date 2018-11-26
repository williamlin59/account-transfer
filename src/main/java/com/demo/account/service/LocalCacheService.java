package com.demo.account.service;

import java.math.BigDecimal;

public interface LocalCacheService {
    void initializeLocalCache();

    void initializeLocalCacheForTest();

    boolean isAccountExist(Long accountId);

    void updateAmount(Long accountId, BigDecimal amount);

    BigDecimal getAmount(Long accountId);

    void updateCachedAccounts();


}
