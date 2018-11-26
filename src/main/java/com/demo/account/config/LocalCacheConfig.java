package com.demo.account.config;

import com.demo.account.domain.LocalAccountCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Local cache for caching transaction data.
 */
@Configuration
public class LocalCacheConfig {
    @Bean
    public LocalAccountCache localAccountCache() {
        return new LocalAccountCache();
    }
}
