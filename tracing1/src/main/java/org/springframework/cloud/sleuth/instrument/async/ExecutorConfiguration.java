package org.springframework.cloud.sleuth.instrument.async;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ExecutorConfiguration {

  @Bean
  public ThreadPoolTaskScheduler threadPoolTaskScheduler(BeanFactory beanFactory) {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    threadPoolTaskScheduler.setPoolSize(4);
    threadPoolTaskScheduler.setThreadNamePrefix("TaskExecutor");
    return new LazyTraceThreadPoolTaskScheduler(beanFactory, threadPoolTaskScheduler);
  }

  @Bean
  public ThreadPoolTaskExecutor threadPoolTaskExecutor(BeanFactory beanFactory) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(20);
    executor.setMaxPoolSize(30);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setThreadNamePrefix("Async-");
    return new LazyTraceThreadPoolTaskExecutor(beanFactory, executor);
  }

}
