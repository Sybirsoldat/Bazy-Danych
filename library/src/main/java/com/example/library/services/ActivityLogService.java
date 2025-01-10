package com.example.library.services;

import com.example.library.models.ActivityLog;
import com.example.library.repositories.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogService {
    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Transactional
    public void deleteActivityLogsByUserId(Long userId) {
        List<ActivityLog> logs = activityLogRepository.findByUser_Id(userId);
        for (ActivityLog log : logs) {
            log.setUser(null);
            activityLogRepository.save(log);
        }
    }


    public List<ActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAll();
    }

    public ActivityLog getActivityLogById(Long id) {
        return activityLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ActivityLog entry not found"));
    }

    public List<ActivityLog> getActivityLogsByUserId(Long userId) {
        return activityLogRepository.findByUser_Id(userId);
    }

    public List<ActivityLog> searchActivityLogsByAction(String action) {
        return activityLogRepository.findByActionContainingIgnoreCase(action);
    }

    public ActivityLog createActivityLog(ActivityLog activityLog) {
        activityLog.setTimestamp(LocalDateTime.now());
        return activityLogRepository.save(activityLog);
    }

    public void deleteActivityLog(Long id) {
        ActivityLog activityLog = getActivityLogById(id);
        activityLogRepository.delete(activityLog);
    }



}
