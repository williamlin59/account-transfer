package com.demo.account.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Configuration class for both app thread pool and schedule task thread pool
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration implements SchedulingConfigurer, AsyncConfigurer {
    @Value("${async.corePoolSize}")
    private Integer asyncCorePoolSize;

    @Value("${async.maxPoolSize}")
    private Integer asyncMaxPoolSize;

    @Value("${async.queueCapacity}")
    private Integer asyncQueueCapacity;

    @Value("${job.poolSize}")
    private Integer scheduledJobPoolSize;

    @Bean
    public Executor getTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncCorePoolSize);
        executor.setMaxPoolSize(asyncMaxPoolSize);
        executor.setQueueCapacity(asyncQueueCapacity);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("async-thread-pool-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(scheduledJobPoolSize, new ThreadFactoryBuilder().setNameFormat("scheduled-job-poo-%d").build());
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }
}
