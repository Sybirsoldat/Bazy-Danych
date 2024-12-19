package com.example.library.controllers;

import com.example.library.models.Genre;
import com.example.library.services.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@Tag(name = "Genres API", description = "API do zarządzania gatunkami książek w systemie bibliotecznym")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie gatunki", description = "Zwraca listę wszystkich gatunków książek w systemie")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz gatunek po ID", description = "Zwraca szczegóły gatunku książki na podstawie jego ID")
    public ResponseEntity<Genre> getGenreById(
            @Parameter(description = "ID gatunku", example = "1")
            @PathVariable Long id) {
        return new ResponseEntity<>(genreService.getGenreById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Pobierz gatunek po nazwie", description = "Zwraca szczegóły gatunku na podstawie jego nazwy")
    public ResponseEntity<Genre> getGenreByName(
            @Parameter(description = "Nazwa gatunku", example = "Fantasy")
            @RequestParam String name) {
        return new ResponseEntity<>(genreService.getGenreByName(name), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Utwórz nowy gatunek", description = "Dodaje nowy gatunek książek do systemu")
    public ResponseEntity<Genre> createGenre(
            @Parameter(description = "Dane nowego gatunku książek")
            @RequestBody Genre genre) {
        return new ResponseEntity<>(genreService.createGenre(genre), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Zaktualizuj gatunek", description = "Aktualizuje istniejący gatunek książek na podstawie jego ID")
    public ResponseEntity<Genre> updateGenre(
            @Parameter(description = "ID gatunku do aktualizacji", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Zaktualizowane dane gatunku")
            @RequestBody Genre genreDetails) {
        return new ResponseEntity<>(genreService.updateGenre(id, genreDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Usuń gatunek", description = "Trwale usuwa gatunek książek na podstawie jego ID")
    public ResponseEntity<Void> deleteGenre(
            @Parameter(description = "ID gatunku do usunięcia", example = "1")
            @PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
