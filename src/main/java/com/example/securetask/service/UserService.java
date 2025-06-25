package com.example.securetask.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.securetask.model.Task;
import com.example.securetask.model.User;
import com.example.securetask.repository.UserRepository;

@Service
public class UserService {

	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

	/*
	 * public Optional<User> updateUser(Long id, User userDetails) { return
	 * userRepository.findById(id).map(user -> {
	 * user.setUsername(userDetails.getUsername());
	 * 
	 * if (userDetails.getPassword() != null) {
	 * user.setPassword(passwordEncoder.encode(userDetails.getPassword())); }
	 * user.setRole(userDetails.getRole()); return userRepository.save(user); }); }
	 */

    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
    public User saveUser(User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }   
    
    
}


