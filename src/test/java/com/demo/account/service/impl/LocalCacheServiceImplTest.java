package com.demo.account.service.impl;

import com.demo.account.domain.AccountEntity;
import com.demo.account.repository.AccountRepository;
import com.demo.account.service.LocalCacheService;
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
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
class LocalCacheServiceImplTest {
    @Autowired
    private LocalCacheService localCacheService;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void tearUp() {
        accountRepository.deleteAll();
        accountRepository.flush();
    }

    @Test
    void initializeLocalCache() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountId(1L);
        accountEntity.setAmount(new BigDecimal(5000));
        accountEntity.setCreateTime(DateTimeUtil.getCurrentLocalDateTime());
        accountRepository.save(accountEntity);
        localCacheService.initializeLocalCache();
        assertThat(localCacheService.getAmount(1L)).isEqualTo(new BigDecimal(5000));
    }

    @Test
    void initializeLocalCacheForTest() {
        localCacheService.initializeLocalCacheForTest();
        LongStream.rangeClosed(1, 10).forEach(i -> assertThat(localCacheService.getAmount(i)).isEqualTo(new BigDecimal(5000)));
    }

    @Test
    void isAccountExist() {
        localCacheService.initializeLocalCacheForTest();
        LongStream.rangeClosed(1, 10).forEach(i -> assertThat(localCacheService.isAccountExist(i)).isTrue());
        assertThat(localCacheService.isAccountExist(500L)).isFalse();
    }

    @Test
    void updateAmount() {
        localCacheService.initializeLocalCacheForTest();
        localCacheService.updateAmount(7L, new BigDecimal(300));
        assertThat(localCacheService.getAmount(7L)).isEqualTo(new BigDecimal(5300));
        localCacheService.updateAmount(6L, new BigDecimal(-300));
        assertThat(localCacheService.getAmount(6L)).isEqualTo(new BigDecimal(4700));
    }


}