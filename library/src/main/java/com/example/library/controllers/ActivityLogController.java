package com.example.library.controllers;

import com.example.library.models.ActivityLog;
import com.example.library.services.ActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activitylog")
@Tag(name = "Activity Log API", description = "API do zarządzania logami aktywności użytkowników w systemie bibliotecznym")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie logi aktywności", description = "Zwraca listę wszystkich logów aktywności w systemie")
    public ResponseEntity<List<ActivityLog>> getAllActivityLogs() {
        return new ResponseEntity<>(activityLogService.getAllActivityLogs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz log aktywności po ID", description = "Zwraca szczegóły logu aktywności na podstawie jego ID")
    public ResponseEntity<ActivityLog> getActivityLogById(
            @Parameter(description = "ID logu aktywności", example = "1")
            @PathVariable Long id) {
        return new ResponseEntity<>(activityLogService.getActivityLogById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Pobierz logi aktywności użytkownika", description = "Zwraca logi aktywności dla konkretnego użytkownika na podstawie jego ID")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByUserId(
            @Parameter(description = "ID użytkownika", example = "1")
            @PathVariable Long userId) {
        return new ResponseEntity<>(activityLogService.getActivityLogsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Wyszukaj logi aktywności po akcji", description = "Zwraca logi aktywności na podstawie podanej nazwy akcji")
    public ResponseEntity<List<ActivityLog>> searchActivityLogsByAction(
            @Parameter(description = "Nazwa akcji logu", example = "login")
            @RequestParam String action) {
        return new ResponseEntity<>(activityLogService.searchActivityLogsByAction(action), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Utwórz nowy log aktywności", description = "Dodaje nowy log aktywności użytkownika")
    public ResponseEntity<ActivityLog> createActivityLog(
            @Parameter(description = "Dane nowego logu aktywności")
            @RequestBody ActivityLog activityLog) {
        return new ResponseEntity<>(activityLogService.createActivityLog(activityLog), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń log aktywności", description = "Usuwa log aktywności na podstawie jego ID")
    public ResponseEntity<Void> deleteActivityLog(
            @Parameter(description = "ID logu aktywności do usunięcia", example = "1")
            @PathVariable Long id) {
        activityLogService.deleteActivityLog(id);
        return ResponseEntity.noContent().build();
    }
}
