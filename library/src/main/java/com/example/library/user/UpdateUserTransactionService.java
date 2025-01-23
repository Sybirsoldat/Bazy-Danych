package com.example.library.user;

import com.example.library.models.ActivityLog;
import com.example.library.services.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserTransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private UserService userService;

    /**
     * Updates user details in a transactional manner.
     *
     * @param userId      The ID of the user to update.
     * @param userDetails The new details for the user.
     * @return The updated User object.
     */
    @Transactional
    public User updateUser(Long userId, User userDetails) {
        // Fetch existing user
        User existingUser = userService.getUserById(userId);

        // Validate unique fields if they are being updated
        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(existingUser.getEmail())) {
            userService.validateUniqueEmail(userDetails.getEmail());
            existingUser.setEmail(userDetails.getEmail());
        }
        if (userDetails.getMobilePhone() != null && !userDetails.getMobilePhone().equals(existingUser.getMobilePhone())) {
            userService.validateUniqueMobilePhone(userDetails.getMobilePhone());
            existingUser.setMobilePhone(userDetails.getMobilePhone());
        }

        // Update other fields
        existingUser.setFirstname(userDetails.getFirstname());
        existingUser.setSurname(userDetails.getSurname());
        existingUser.setAddress(userDetails.getAddress());

        // Save updated user
        User updatedUser = userRepository.save(existingUser);

        // Log the activity
        ActivityLog log = new ActivityLog();
        log.setUser(updatedUser);
        log.setAction("Updated user details for ID: " + userId);
        activityLogService.createActivityLog(log);

        return updatedUser;
    }
}
