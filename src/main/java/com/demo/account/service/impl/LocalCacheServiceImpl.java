package com.demo.account.service.impl;

import com.demo.account.config.Constants;
import com.demo.account.domain.AccountEntity;
import com.demo.account.domain.LocalAccountCache;
import com.demo.account.repository.AccountRepository;
import com.demo.account.service.LocalCacheService;
import com.demo.account.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Service for initialize account cache from database(integrated mode)
 * or adding dummy account value for testing (embedded mode)
 * it also provide update method to localCacheBackUpJob.
 */
@Service
@Slf4j
public class LocalCacheServiceImpl implements LocalCacheService {
    private final AccountRepository accountRepository;
    private final BigDecimal defaultAccountAmount;
    private final LocalAccountCache localAccountCache;

    @Inject
    LocalCacheServiceImpl(AccountRepository accountRepository,
                          LocalAccountCache localAccountCache,
                          @Value("#{new java.math.BigDecimal(${default-account-amount})}") BigDecimal defaultAccountAmount) {
        this.accountRepository = accountRepository;
        this.defaultAccountAmount = defaultAccountAmount;
        this.localAccountCache = localAccountCache;
    }

    @Profile(Constants.INTEGRATED)
    @Override
    @PostConstruct
    public void initializeLocalCache() {
        accountRepository.findAll().forEach(account -> localAccountCache.putAccount(account.getAccountId(), account.getAmount()));
    }

    @Profile(Constants.EMBEDDED)
    @Override
    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initializeLocalCacheForTest() {
        List<AccountEntity> accountEntities = accountRepository.saveAll(LongStream.range(1, 11).
                mapToObj(i -> new AccountEntity(i, defaultAccountAmount, DateTimeUtil.getCurrentLocalDateTime())).
                collect(Collectors.toList()));
        accountEntities.forEach(accountEntity -> localAccountCache.putAccount(accountEntity.getAccountId(), accountEntity.getAmount()));
    }

    @Override
    public boolean isAccountExist(Long accountId) {
        return localAccountCache.isAccountExist(accountId);
    }

    @Override
    public void updateAmount(Long accountId, BigDecimal amount) {
        localAccountCache.updateAmount(accountId, amount);
    }

    @Override
    public BigDecimal getAmount(Long accountId) {
        return localAccountCache.getAmount(accountId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCachedAccounts() {
        localAccountCache.
                getLocalCache().
                forEach((key, value) -> {
                    accountRepository.updateAmountForAccountId(value, key);
                    log.info("Persisted accountEntity : accountId: {} , amount: {}", key, value);
                });

    }


}
