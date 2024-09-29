package com.neothedeveloper.taskflow_server.service.Implementation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neothedeveloper.taskflow_server.exception.InvalidInputException;
import com.neothedeveloper.taskflow_server.exception.TaskNotFoundException;
import com.neothedeveloper.taskflow_server.model.Task;
import com.neothedeveloper.taskflow_server.repository.TaskRepository;
import com.neothedeveloper.taskflow_server.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private OpenAiService openAiService;

    @Override
    public Task saveTask(Task task) {
       validateTask(task);
       return taskRepository.save(task);  
        
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return Optional.of(taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id)));
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task updateTask(Task task, Long id) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if(existingTask.isPresent()){

            Task updatedTask = existingTask.get();

           updatedTask.setTitle(task.getTitle());
           updatedTask.setDescription(task.getDescription());
           updatedTask.setDueDate(task.getDueDate());
           updatedTask.setCompleted(task.getCompleted());
           updatedTask.setPriority(task.getPriority());

           return taskRepository.save(updatedTask);
        }else{
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
    }

    @Override
    public void deleteTask(Long id) {
        if(!taskRepository.existsById(id)){
            throw new TaskNotFoundException("TaSk not found with id: " + id);
        }

        taskRepository.deleteById(id);
    }

    public List<String> suggestTask(String userInput) {
        return openAiService.getTaskSuggestions(userInput); 
    }
    

    @Override
     public LocalDate predictDueDate(Long id) {
 
        Optional foundTask = getTaskById(id);
        
        List<Task> completedTasks = taskRepository.findByCompletedTrue();
        
        if (completedTasks.isEmpty()) {
            return LocalDate.now().plusDays(3); 
        }

        long totalDays = 0;
        int count = 0;

        for (Task completedTask : completedTasks) {
            if (completedTask.getCompletionDate() != null) {
                long days = ChronoUnit.DAYS.between(completedTask.getDueDate(), completedTask.getCompletionDate());
                totalDays += days;
                count++;
            }
        }

       
        long averageDays = count > 0 ? totalDays / count : 3; 

        LocalDate predictedDate = LocalDate.now().plusDays((int) averageDays);
      
        return predictedDate;
    }


    public Task createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        
        if (task.getRecurrence() != null && !task.getRecurrence().isEmpty()) {
            scheduleRecurringTask(savedTask);
        }

        return savedTask;
    }


    private void scheduleRecurringTask(Task task) {
        LocalDate nextDueDate = null;

        switch (task.getRecurrence().toLowerCase()) {
            case "daily":
                nextDueDate = task.getDueDate().plusDays(1);
                break;
            case "weekly":
                nextDueDate = task.getDueDate().plusWeeks(1);
                break;
            case "monthly":
                nextDueDate = task.getDueDate().plusMonths(1);
                break;
            default:
                return; 
        }

        
        Task recurringTask = new Task();
        recurringTask.setTitle(task.getTitle());
        recurringTask.setDescription(task.getDescription());
        recurringTask.setDueDate(nextDueDate);
        recurringTask.setCompleted(false);
        recurringTask.setPriority(task.getPriority());
        recurringTask.setRecurrence(task.getRecurrence());

        
        taskRepository.save(recurringTask);
    }


    private void validateTask(Task task){
        if(task.getTitle() == null || task.getTitle().isEmpty()){
            throw new InvalidInputException("Title cannot be null or empty");
        }

        if(task.getDescription() == null || task.getDescription().isEmpty()){
            throw new InvalidInputException("Description cannot be null or empty");
        }

        if(task.getDueDate() == null){
            throw new InvalidInputException("Due date cannot be null or empty");
        }

        if(task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())){
            throw new InvalidInputException("Due date cannot be in the past.");
        }
    }
    
}
