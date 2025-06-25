package com.example.securetask.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.securetask.model.Task;
import com.example.securetask.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setPriority(taskDetails.getPriority());
            task.setDeadline(taskDetails.getDeadline());
            task.setCompleted(taskDetails.isCompleted());
            task.setUser(taskDetails.getUser());
            return taskRepository.save(task);
        });
    }

    public boolean deleteTask(Long id) {
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }
    
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

}
