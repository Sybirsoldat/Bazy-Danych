package com.example.librarysystem.repositories;

import com.example.librarysystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);  // Dodatkowa metoda do szukania użytkownika po loginie
}