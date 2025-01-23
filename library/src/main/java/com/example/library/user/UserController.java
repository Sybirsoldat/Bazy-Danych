package com.example.library.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users API", description = "API do zarządzania użytkownikami w systemie bibliotecznym")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkich użytkowników", description = "Zwraca listę wszystkich użytkowników w systemie")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz użytkownika po ID", description = "Zwraca szczegóły użytkownika na podstawie jego unikalnego ID")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID użytkownika", example = "1")
            @PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/login")
    @Operation(summary = "Pobierz użytkownika po loginie", description = "Zwraca użytkownika na podstawie jego loginu")
    public ResponseEntity<User> getUserByLogin(
            @Parameter(description = "Login użytkownika", example = "admin")
            @RequestParam String login) {
        return new ResponseEntity<>(userService.getUserByLogin(login), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Utwórz nowego użytkownika", description = "Dodaje nowego użytkownika do systemu")
    public ResponseEntity<String> createUser(
            @Parameter(description = "Dane nowego użytkownika")
            @RequestBody User user) {
        String createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń użytkownika", description = "Trwale usuwa użytkownika na podstawie jego ID")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID użytkownika do usunięcia", example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
