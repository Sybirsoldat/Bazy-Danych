package com.example.library.controllers;

import com.example.library.models.ActivityLog;
import com.example.library.services.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activitylog")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    public ResponseEntity<List<ActivityLog>> getAllActivityLogs() {
        return new ResponseEntity<>(activityLogService.getAllActivityLogs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLogById(@PathVariable Long id) {
        return new ResponseEntity<>(activityLogService.getActivityLogById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(activityLogService.getActivityLogsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ActivityLog>> searchActivityLogsByAction(@RequestParam String action) {
        return new ResponseEntity<>(activityLogService.searchActivityLogsByAction(action), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ActivityLog> createActivityLog(@RequestBody ActivityLog activityLog) {
        return new ResponseEntity<>(activityLogService.createActivityLog(activityLog), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable Long id) {
        activityLogService.deleteActivityLog(id);
        return ResponseEntity.noContent().build();
    }
}
