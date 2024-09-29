package com.neothedeveloper.taskflow_server.service.Implementation;

import com.neothedeveloper.taskflow_server.model.Task;
import com.neothedeveloper.taskflow_server.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {

    private final TaskRepository taskRepository;

    public ReminderService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(fixedRate = 60000) 
    public void sendReminderNotifications() {
        LocalDate now = LocalDate.now();
        LocalDate twentyFourHoursLater = now.plusDays(1);

        List<Task> tasksDueSoon = taskRepository.findByDueDateBetween(now, twentyFourHoursLater);

        for (Task task : tasksDueSoon) {
            System.out.println("Reminder: Task '" + task.getTitle() + "' is due soon on " + task.getDueDate());
        }
    }
}
