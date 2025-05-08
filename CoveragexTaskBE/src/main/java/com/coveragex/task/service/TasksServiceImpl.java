package com.coveragex.task.service;

import com.coveragex.task.exception.GlobalExceptionHandler;
import com.coveragex.task.models.Task;
import com.coveragex.task.models.TaskDTO;
import com.coveragex.task.models.TaskStatus;
import com.coveragex.task.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TasksServiceImpl implements TasksService {
    private static final Logger logger = LoggerFactory.getLogger(TasksServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Adds a new task.
     * @param addTask the task to add
     * @return the added task
     */
    @Override
    public TaskDTO addTasks(TaskDTO addTask){
        try {
            Task task = mapToEntity(addTask);
            Task savedTask = taskRepository.save(task);
            return mapToDto(savedTask);
        } catch (Exception e) {
            logger.error("Error adding task", e);
            throw new GlobalExceptionHandler.CustomException("Failed to add task");
        }
    }

    /**
     * Returns a list of recent tasks.
     * @return a list of recent tasks
     */
    @Override
    public List<TaskDTO> getRecentTasks() {
        try {
            return taskRepository.mostRecentTasks(TaskStatus.PENDING.name()).stream()
                    .map(this::mapToDto)
                    .toList();
        }catch (Exception e) {
            logger.error("Error getting recent tasks", e);
            throw new GlobalExceptionHandler.CustomException("Failed to get tasks");
        }
    }

    /**
     * Complete a task by its ID.
     *
     * @param taskId the ID of the task to complete
     * @return the completed task
     * @throws GlobalExceptionHandler.CustomException if the task cannot be found or completed
     */
    public TaskDTO completeTask(int taskId) {
        try {
            Task task = taskRepository.findById(taskId).orElseThrow();
            task.setStatus(TaskStatus.COMPLETED);
            taskRepository.save(task);
            return mapToDto(task);
        } catch (Exception e) {
            logger.error("Error completing task", e);
            throw new GlobalExceptionHandler.CustomException("Failed to complete task");
        }
    }
    /**
     * Maps a TaskDTO to a Task entity.
     *
     * @param dto The DTO to be mapped.
     * @return The mapped entity.
     */
    private Task mapToEntity(TaskDTO dto) {
        Task task = new Task();
        task.setDescription(dto.getDescription());
        task.setTitle(dto.getTitle());
        task.setStatus(TaskStatus.PENDING);
        return task;

    }

    /**
     * Maps a  Task entity to a TaskDTO.
     *
     * @param entity the entity to map
     * @return the mapped dto
     */
    private TaskDTO mapToDto(Task entity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(entity.getId());
        taskDTO.setTitle(entity.getTitle());
        taskDTO.setDescription(entity.getDescription());
        return taskDTO;
    }
}