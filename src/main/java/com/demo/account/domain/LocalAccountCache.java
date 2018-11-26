package com.demo.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * POJO class for local cache using concurrentHashMap
 * Key: accountId (Long)
 * Value: currentAmount (BigDecimal)
 */

public class LocalAccountCache implements Serializable {
    private static final long serialVersionUID = 6591035949382516269L;
    private final Map<Long, BigDecimal> localCache;

    public LocalAccountCache() {
        localCache = new ConcurrentHashMap<>();
    }

    public void putAccount(Long accountId, BigDecimal amount) {
        localCache.put(accountId, amount);
    }

    public BigDecimal getAmount(Long accountId) {
        return localCache.get(accountId);
    }

    public void updateAmount(Long accountId, BigDecimal amount) {
        localCache.computeIfPresent(accountId, (key, value) -> value.add(amount));
    }

    public Map<Long, BigDecimal> getLocalCache() {
        return localCache;
    }

    public boolean isAccountExist(Long accountId) {
        return localCache.containsKey(accountId);
    }
}
