package com.neothedeveloper.taskflow_server.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.neothedeveloper.taskflow_server.model.Task;

public interface TaskService {
    
    public Task saveTask(Task task);

    public Optional<Task> getTaskById(Long id);

    public List<Task> getAllTasks();

    public Task updateTask(Task task, Long id);

    public void deleteTask(Long id);

    public LocalDate predictDueDate(Long id);

}
