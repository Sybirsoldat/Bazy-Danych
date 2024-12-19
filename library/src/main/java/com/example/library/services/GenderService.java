package com.example.library.services;

import com.example.library.models.Gender;
import com.example.library.repositories.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenderService {

    @Autowired
    private GenderRepository genderRepository;

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }

    public Gender getGenderById(Long id) {
        return genderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gender not found"));
    }

    public Gender getGenderByType(String type) {
        return genderRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("Gender not found"));
    }

    public Gender createGender(Gender gender) {
        return genderRepository.save(gender);
    }

    public Gender updateGender(Long id, Gender genderDetails) {
        Gender gender = getGenderById(id);
        gender.setType(genderDetails.getType());
        return genderRepository.save(gender);
    }

    public void deleteGender(Long id) {
        Gender gender = getGenderById(id);
        genderRepository.delete(gender);
    }
}