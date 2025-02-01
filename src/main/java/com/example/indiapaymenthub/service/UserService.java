package com.example.indiapaymenthub.service;

import com.example.indiapaymenthub.model.User;

import com.example.indiapaymenthub.service.UserService;
import com.example.indiapaymenthub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }
    // Method to get all users
    public List<User> getUsers() {
        return userRepository.findAll(); // Fetches all users from the repository
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
