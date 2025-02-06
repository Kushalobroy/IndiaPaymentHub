package com.example.indiapaymenthub.service;

import com.example.indiapaymenthub.model.User;

import com.example.indiapaymenthub.service.UserService;
import com.example.indiapaymenthub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

@Service
public class  UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

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
    // Generate Token and Send Email
    public void sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email);  // Get existing user
        if (user != null) {
            String token = UUID.randomUUID().toString(); // Generate a random token
            user.setResetToken(token);
                userRepository.save(user);

                // Send Email
                sendEmail(user.getEmail(), token);
        }
    }
    private void sendEmail(String email, String token) {
        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        String subject = "Password Reset Request";
        String body = "Click the link to reset your password: " + resetLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    // Reset Password
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(newPassword);
            user.setResetToken(null); // Clear the token
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
