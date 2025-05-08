package com.coveragex.task.service;

import com.coveragex.task.exception.GlobalExceptionHandler;
import com.coveragex.task.models.Task;
import com.coveragex.task.models.TaskDTO;
import com.coveragex.task.models.TaskStatus;
import com.coveragex.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TasksServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TasksServiceImpl tasksService;

    private TaskDTO sampleDto;
    private Task sampleTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleDto = new TaskDTO();
        sampleDto.setTitle("Sample Task");
        sampleDto.setDescription("Sample Description");

        sampleTask = new Task();
        sampleTask.setId(1);
        sampleTask.setTitle("Sample Task");
        sampleTask.setDescription("Sample Description");
        sampleTask.setStatus(TaskStatus.PENDING);
    }

    @Test
    void addTasks_ShouldReturnSavedTaskDTO() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        TaskDTO result = tasksService.addTasks(sampleDto);

        assertEquals("Sample Task", result.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void addTasks_ShouldThrowCustomException_OnFailure() {
        when(taskRepository.save(any())).thenThrow(RuntimeException.class);

        GlobalExceptionHandler.CustomException ex = assertThrows(
                GlobalExceptionHandler.CustomException.class,
                () -> tasksService.addTasks(sampleDto)
        );

        assertEquals("Failed to add task", ex.getMessage());
    }

    @Test
    void getRecentTasks_ShouldReturnMappedDTOs() {
        List<Task> tasks = List.of(sampleTask);
        when(taskRepository.mostRecentTasks(TaskStatus.PENDING.name())).thenReturn(tasks);

        List<TaskDTO> result = tasksService.getRecentTasks();

        assertEquals(1, result.size());
        assertEquals("Sample Task", result.get(0).getTitle());
        verify(taskRepository).mostRecentTasks(TaskStatus.PENDING.name());
    }

    @Test
    void getRecentTasks_ShouldThrowCustomException_OnError() {
        when(taskRepository.mostRecentTasks(anyString())).thenThrow(RuntimeException.class);

        assertThrows(GlobalExceptionHandler.CustomException.class, () -> tasksService.getRecentTasks());
    }

    @Test
    void completeTask_ShouldSetStatusAndReturnDTO() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        TaskDTO result = tasksService.completeTask(1);

        assertEquals(TaskStatus.COMPLETED, sampleTask.getStatus());
        assertEquals("Sample Task", result.getTitle());
    }

    @Test
    void completeTask_ShouldThrowCustomException_IfTaskNotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptionHandler.CustomException.class, () -> tasksService.completeTask(1));
    }
}