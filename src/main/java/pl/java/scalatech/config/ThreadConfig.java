package pl.java.scalatech.config;

import java.util.concurrent.ThreadFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Configuration
@EnableScheduling
@EnableAsync
public class ThreadConfig {

    @Bean(name = "executor")
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setCorePoolSize(5);
        // threadPoolTaskExecutor.setThreadFactory(threadFactory());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    public ThreadFactory threadFactory() {
        return new ThreadFactoryBuilder().setNameFormat("my-pool-id").build();
    }

}
