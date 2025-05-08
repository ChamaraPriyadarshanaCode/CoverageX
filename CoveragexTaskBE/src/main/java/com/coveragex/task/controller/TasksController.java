package com.coveragex.task.controller;

import com.coveragex.task.models.TaskDTO;
import com.coveragex.task.service.TasksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {
    private final TasksService tasksService;

    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    /**
     * Adds a new task.
     *
     * @param addTask the task to add
     * @return the added task
     */
    @PostMapping
    public ResponseEntity<TaskDTO> addTask(@Valid @RequestBody TaskDTO addTask) {
        TaskDTO addTasks = tasksService.addTasks(addTask);
        return new ResponseEntity<>(addTasks, HttpStatus.CREATED);
    }
    /**
     * Returns a list of recent tasks.
     *
     * @return a list of recent tasks
     */
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getRecentTasks() {
        return ResponseEntity.ok(tasksService.getRecentTasks());
    }
    /**
     * Marks a task as completed based on its ID.
     *
     * @param taskId the ID of the task to complete
     * @return the completed task as a TaskDTO
     */
    @PutMapping("/{taskId}/complete")
    public TaskDTO completeTask(@PathVariable int taskId) {
        return tasksService.completeTask(taskId);
    }
}
