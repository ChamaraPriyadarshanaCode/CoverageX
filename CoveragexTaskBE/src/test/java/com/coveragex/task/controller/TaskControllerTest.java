package com.coveragex.task.controller;

import com.coveragex.task.config.TestConfig;
import com.coveragex.task.models.TaskDTO;
import com.coveragex.task.service.TasksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TasksController.class)
@Import(TestConfig.class)
class TasksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddTask_ReturnsCreated() throws Exception {
        TaskDTO task = new TaskDTO();
        task.setId(1);
        task.setTitle("Test Task");
        task.setDescription("Description");

        Mockito.when(tasksService.addTasks(any(TaskDTO.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void testGetRecentTasks_ReturnsList() throws Exception {
        TaskDTO task1 = new TaskDTO();
        task1.setId(1);
        task1.setTitle("title 1");
        task1.setDescription("Task 1");

        TaskDTO task2 = new TaskDTO();
        task2.setId(2);
        task2.setTitle("title 2");
        task2.setDescription("Desc 2");

        Mockito.when(tasksService.getRecentTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testCompleteTask_ReturnsCompletedTask() throws Exception {
        TaskDTO completedTask = new TaskDTO();
        completedTask.setId(1);
        completedTask.setTitle("Test Task");
        completedTask.setDescription("Description");

        Mockito.when(tasksService.completeTask(1)).thenReturn(completedTask);

        mockMvc.perform(put("/api/tasks/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testAddTask_InvalidInput_ReturnsBadRequest() throws Exception {
        TaskDTO invalidTask = new TaskDTO();

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest());
    }

}
