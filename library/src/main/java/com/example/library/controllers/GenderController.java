package com.example.library.controllers;

import com.example.library.models.Gender;
import com.example.library.services.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @GetMapping
    public ResponseEntity<List<Gender>> getAllGenders() {
        return new ResponseEntity<>(genderService.getAllGenders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gender> getGenderById(@PathVariable Long id) {
        return new ResponseEntity<>(genderService.getGenderById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Gender> getGenderByType(@RequestParam String type) {
        return new ResponseEntity<>(genderService.getGenderByType(type), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Gender> createGender(@RequestBody Gender gender) {
        return new ResponseEntity<>(genderService.createGender(gender), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gender> updateGender(@PathVariable Long id, @RequestBody Gender genderDetails) {
        return new ResponseEntity<>(genderService.updateGender(id, genderDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGender(@PathVariable Long id) {
        genderService.deleteGender(id);
        return ResponseEntity.noContent().build();
    }
}
