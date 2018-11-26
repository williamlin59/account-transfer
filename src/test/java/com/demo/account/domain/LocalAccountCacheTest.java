package com.demo.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class LocalAccountCacheTest {
    private static final BigDecimal DEFAULT_FUND = new BigDecimal(100);
    private LocalAccountCache localAccountCache;

    @BeforeEach
    void setUp() {
        localAccountCache = new LocalAccountCache();
    }

    @Test
    void putAccountTest() {
        localAccountCache.putAccount(1L, DEFAULT_FUND);
        assertThat(localAccountCache.getAmount(1L)).isEqualTo(DEFAULT_FUND);
    }

    @Test
    void updateAmount() {
        localAccountCache.putAccount(1L, DEFAULT_FUND);
        localAccountCache.updateAmount(1L, new BigDecimal(300));
        assertThat(localAccountCache.getAmount(1L)).isEqualTo(new BigDecimal(400));
    }

    @Test
    void getLocalCache() {
        assertThat(localAccountCache.getLocalCache()).isNotNull();
    }

    @Test
    void isAccountExist() {
        localAccountCache.putAccount(1L, DEFAULT_FUND);
        assertThat(localAccountCache.isAccountExist(1L)).isTrue();
    }
}