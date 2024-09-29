package com.neothedeveloper.taskflow_server.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neothedeveloper.taskflow_server.model.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByCompleted(Boolean completed);
    List<Task> findByCompletedTrue();
    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
    
} 