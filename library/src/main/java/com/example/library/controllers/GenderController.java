package com.example.library.controllers;

import com.example.library.models.Gender;
import com.example.library.services.GenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genders")
@Tag(name = "Genders API", description = "API do zarządzania płciami użytkowników w systemie bibliotecznym")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie płcie", description = "Zwraca listę wszystkich płci użytkowników w systemie")
    public ResponseEntity<List<Gender>> getAllGenders() {
        return new ResponseEntity<>(genderService.getAllGenders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz płeć po ID", description = "Zwraca szczegóły płci na podstawie jej ID")
    public ResponseEntity<Gender> getGenderById(
            @Parameter(description = "ID płci", example = "1")
            @PathVariable Long id) {
        return new ResponseEntity<>(genderService.getGenderById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Pobierz płeć po nazwie", description = "Zwraca szczegóły płci na podstawie jej nazwy")
    public ResponseEntity<Gender> getGenderByType(
            @Parameter(description = "Nazwa płci", example = "Mężczyzna")
            @RequestParam String type) {
        return new ResponseEntity<>(genderService.getGenderByType(type), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Utwórz nową płeć", description = "Dodaje nową płeć użytkowników do systemu")
    public ResponseEntity<Gender> createGender(
            @Parameter(description = "Dane nowej płci użytkownika")
            @RequestBody Gender gender) {
        return new ResponseEntity<>(genderService.createGender(gender), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Zaktualizuj płeć", description = "Aktualizuje istniejącą płeć użytkownika na podstawie jej ID")
    public ResponseEntity<Gender> updateGender(
            @Parameter(description = "ID płci do aktualizacji", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Zaktualizowane dane płci")
            @RequestBody Gender genderDetails) {
        return new ResponseEntity<>(genderService.updateGender(id, genderDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń płeć", description = "Trwale usuwa płeć użytkownika na podstawie jej ID")
    public ResponseEntity<Void> deleteGender(
            @Parameter(description = "ID płci do usunięcia", example = "1")
            @PathVariable Long id) {
        genderService.deleteGender(id);
        return ResponseEntity.noContent().build();
    }
}
