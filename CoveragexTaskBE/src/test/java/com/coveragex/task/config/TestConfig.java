package com.coveragex.task.config;

import com.coveragex.task.service.TasksService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public TasksService tasksService() {
        return Mockito.mock(TasksService.class);
    }
}
