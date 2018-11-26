package com.demo.account.job;

import com.demo.account.service.LocalCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * This is job periodically backUp cache data to database
 * Currently configured to every minute
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class LocalCacheBackUpJob {
    private LocalCacheService localCacheService;

    @Scheduled(cron = "${job.cron}", zone = "UTC")
    public void backUpCacheData() {
        log.info("Start backUp local cache ...");
        localCacheService.updateCachedAccounts();
    }
}
