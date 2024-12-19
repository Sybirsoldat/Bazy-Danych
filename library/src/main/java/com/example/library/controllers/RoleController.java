package com.example.library.controllers;

import com.example.library.models.Role;
import com.example.library.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles API", description = "API do zarządzania rolami użytkowników w systemie bibliotecznym")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie role", description = "Zwraca listę wszystkich ról użytkowników w systemie")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz rolę po ID", description = "Zwraca szczegóły roli na podstawie jej ID")
    public ResponseEntity<Role> getRoleById(
            @Parameter(description = "ID roli", example = "1")
            @PathVariable Long id) {
        return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Pobierz rolę po nazwie", description = "Zwraca rolę użytkownika na podstawie nazwy roli")
    public ResponseEntity<Role> getRoleByName(
            @Parameter(description = "Nazwa roli", example = "Administrator")
            @RequestParam String roleName) {
        return new ResponseEntity<>(roleService.getRoleByName(roleName), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Utwórz nową rolę", description = "Dodaje nową rolę użytkownika do systemu")
    public ResponseEntity<Role> createRole(
            @Parameter(description = "Dane nowej roli użytkownika")
            @RequestBody Role role) {
        return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Zaktualizuj rolę", description = "Aktualizuje istniejącą rolę użytkownika na podstawie ID")
    public ResponseEntity<Role> updateRole(
            @Parameter(description = "ID roli do aktualizacji", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Zaktualizowane dane roli")
            @RequestBody Role roleDetails) {
        return new ResponseEntity<>(roleService.updateRole(id, roleDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń rolę", description = "Trwale usuwa rolę użytkownika na podstawie jej ID")
    public ResponseEntity<Void> deleteRole(
            @Parameter(description = "ID roli do usunięcia", example = "1")
            @PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
