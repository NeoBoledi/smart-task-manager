package com.neothedeveloper.taskflow_server.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDTO {

    @NotBlank(message = "Title is required")
    @NotNull
    @Size(min =2, max= 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "Title is required")
    @NotNull
    @Size(min =10, max= 500, message = "Title must be between 2 and 100 characters")
    private String description;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    private Boolean completed;

    private String priority;

    private String recurrence;

    private LocalDateTime completionDate;
}
