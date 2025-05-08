package com.coveragex.task.repository;

import com.coveragex.task.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value="SELECT * FROM task WHERE status = :status ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Task> mostRecentTasks(String status);
}
