package com.neothedeveloper.taskflow_server.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.neothedeveloper.taskflow_server.dto.TaskDTO;
import com.neothedeveloper.taskflow_server.exception.TaskNotFoundException;
import com.neothedeveloper.taskflow_server.mapper.TaskMapper;
import com.neothedeveloper.taskflow_server.model.Task;
import com.neothedeveloper.taskflow_server.service.TaskService;
import com.neothedeveloper.taskflow_server.service.Implementation.OpenAiService;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private OpenAiService openAiService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = TaskMapper.toEntity(taskDTO);
        Task savedTask = taskService.saveTask(task);
        TaskDTO response = TaskMapper.toDTO(savedTask);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            TaskDTO response = TaskMapper.toDTO(task.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new TaskNotFoundException("Task not found with id " + id);
        }
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = tasks.stream().map(TaskMapper::toDTO).toList();
        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        Task task = TaskMapper.toEntity(taskDTO);
        Task updatedTask = taskService.updateTask(task, id);
        TaskDTO response = TaskMapper.toDTO(updatedTask);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/suggest")
    public ResponseEntity<List<String>> suggestTask(@RequestBody String userInput) {
        List<String> suggestions = openAiService.getTaskSuggestions(userInput);
        return ResponseEntity.ok(suggestions);
    }

    @PostMapping("/{id}/predict-due-date")
    public ResponseEntity<LocalDate> predictDueDate(@PathVariable Long id) {
        LocalDate predictedDate = taskService.predictDueDate(id);
        return ResponseEntity.ok(predictedDate);
    }
}
