package com.demo.account.job;

import com.demo.account.service.LocalCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class LocalCacheBackUpJobTest {

    @Mock
    private LocalCacheService localCacheServiceMock;

    private LocalCacheBackUpJob localCacheBackUpJob;

    @BeforeEach
    void setUp() {
        initMocks(this);
        localCacheBackUpJob = new LocalCacheBackUpJob(localCacheServiceMock);

    }

    @Test
    void backUpCacheData() {
        doNothing().when(localCacheServiceMock).updateCachedAccounts();
        localCacheBackUpJob.backUpCacheData();
        verify(localCacheServiceMock).updateCachedAccounts();
    }
}