package com.example.securetask.service;

import com.example.securetask.model.Task;
import com.example.securetask.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskReminderService {

    private final TaskRepository taskRepository;

    @Scheduled(cron = "0 0 9 * * ?") 
    public void checkDeadlines() {
        List<Task> lateTasks = taskRepository.findByDeadlineBefore(LocalDateTime.now());
        
        if (lateTasks.isEmpty()) {
            log.info("No Tasks for today");
        } else {
            log.warn(" Late Tasks");
            lateTasks.forEach(task -> 
                log.warn("🔸 {} - deadline: {}", task.getTitle(), task.getDeadline())
            );
        }
    }
}
