package com.example.securetask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securetask.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
