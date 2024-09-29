package com.neothedeveloper.taskflow_server.mapper;

import com.neothedeveloper.taskflow_server.dto.TaskDTO;
import com.neothedeveloper.taskflow_server.model.Task;

public class TaskMapper{

    // Entity to DTO
    public static TaskDTO toDTO(Task task){
        return new TaskDTO(
            task.getTitle(),
            task.getDescription(),
            task.getDueDate(),
            task.getCompleted(),
            task.getPriority(),
            task.getRecurrence(),
            task.getCompletionDate()
        );
    }

    // DTO to Entity

    public static Task toEntity(TaskDTO taskDTO){
        
        Task task = new Task();

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setCompleted(taskDTO.getCompleted());
        task.setPriority(taskDTO.getPriority());
        task.setRecurrence(taskDTO.getRecurrence());
        task.setCompletionDate(taskDTO.getCompletionDate());

        return task;
    }
}