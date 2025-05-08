package com.coveragex.task.service;

import com.coveragex.task.models.TaskDTO;

import java.util.List;

public interface TasksService {
    TaskDTO addTasks(TaskDTO addTask);

    List<TaskDTO> getRecentTasks();

    TaskDTO completeTask(int taskId);

}
