package com.example.library.services;

import com.example.library.models.User;
import com.example.library.repositories.UserRepository;
import com.example.library.models.ActivityLog;
import com.example.library.services.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityLogService activityLogService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        validateUniqueFields(user);
        User savedUser = userRepository.save(user);

        // Rejestracja aktywności
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUser(savedUser);
        activityLog.setAction("Created a new user");
        activityLogService.createActivityLog(activityLog);

        return savedUser;
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = getUserById(id);

        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(existingUser.getEmail())) {
            validateUniqueEmail(userDetails.getEmail());
            existingUser.setEmail(userDetails.getEmail());
        }
        if (userDetails.getMobilePhone() != null && !userDetails.getMobilePhone().equals(existingUser.getMobilePhone())) {
            validateUniqueMobilePhone(userDetails.getMobilePhone());
            existingUser.setMobilePhone(userDetails.getMobilePhone());
        }
        existingUser.setFirstname(userDetails.getFirstname());
        existingUser.setSurname(userDetails.getSurname());
        existingUser.setAddress(userDetails.getAddress());
        existingUser.setStatus(userDetails.getStatus());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    void validateUniqueFields(User user) {
        validateUniqueEmail(user.getEmail());
        validateUniqueMobilePhone(user.getMobilePhone());
    }

    void validateUniqueEmail(String email) {
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new RuntimeException("Email already exists");
        });
    }

    void validateUniqueMobilePhone(String mobilePhone) {
        userRepository.findByMobilePhone(mobilePhone).ifPresent(u -> {
            throw new RuntimeException("Mobile phone already exists");
        });
    }
}
