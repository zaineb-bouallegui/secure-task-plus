package com.example.securetask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securetask.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);

}
