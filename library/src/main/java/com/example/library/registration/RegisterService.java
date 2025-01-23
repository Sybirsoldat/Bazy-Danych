package com.example.library.registration;

import com.example.library.models.ActivityLog;
import com.example.library.models.Gender;
import com.example.library.repositories.GenderRepository;
import com.example.library.repositories.RoleRepository;
import com.example.library.services.ActivityLogService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.library.registration.token.ConfirmationToken;
import com.example.library.registration.token.ConfirmationTokenService;
import com.example.library.user.User;
import com.example.library.email.EmailSender;
import com.example.library.user.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RegisterService {

    @Autowired
    private ActivityLogService activityLogService;
    private final UserService userService;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final RoleRepository roleRepository;
    private final GenderRepository genderRepository;


    public Map<String, Object> register(RegisterRequest request) {
        // Logowanie aktywności
        ActivityLog log = new ActivityLog();
        log.setAction("New registration");
        log.setTimestamp(LocalDateTime.now());
        activityLogService.createActivityLog(log);

        boolean isValidEmail = emailValidator.test(request.getEmail());
        boolean isValidPassword = passwordValidator.test(request.getPassword());

        if (!isValidEmail)
            throw new IllegalStateException("Email nie jest poprawny");

        if (!isValidPassword)
            throw new IllegalStateException("Hasło nie spełnia wymagań bezpieczeństwa");

        // Pobranie istniejącej encji Gender z bazy danych
        Gender gender = genderRepository.findById(request.getGenderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid gender ID"));

        String token = userService.createUser(
                new User(
                        request.getUsername(),
                        request.getPassword(),
                        request.getEmail(),
                        request.getFirstName(),
                        request.getSurname(),
                        request.getPatronymic(),
                        gender,  // Przypisanie istniejącego obiektu Gender
                        request.getAddress(),
                        request.getMobilePhone(),
                        roleRepository.findById(3L)
                                .orElseThrow(() -> new RuntimeException("Role not found")), // Pobranie roli o ID 3
                        false,  // locked (konto nie jest zablokowane)
                        false   // enabled (konto nie jest aktywne do potwierdzenia)
                )
        );

        String link = "http://localhost:8080/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildConfirmationEmail(request.getFirstName(), link),
                "Aktywacja konta"
        );

        confirmToken(token);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Email aktywacyjny został wysłany na Twoją skrzynkę pocztową");
        response.put("error", HttpStatus.OK.value());
        response.put("timestamp", new Timestamp(new Date().getTime()));
        response.put("redirect", "http://localhost:8080/login");
        response.put("token", token);
        return response;
    }


    private String buildConfirmationEmail(String firstName, String link) {
        return "Witaj " + firstName + ",<br/>" +
                "Kliknij <a href=\"" + link + "\">tutaj</a>, aby aktywować konto";
    }

    @Transactional
    public Map<String, Object> confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken == null) {
            throw new IllegalStateException("Token nie istnieje");
        }

        User user = confirmationToken.getUser();
        System.out.println("Próba aktywacji użytkownika: " + user.getEmail());

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Adres email jest już potwierdzony");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(user.getEmail());
        user.setEnabled(true);
        System.out.println("Status użytkownika po aktywacji: " + user.getEnabled());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Konto zostało aktywowane");
        response.put("error", HttpStatus.OK.value());
        response.put("timestamp", new Timestamp(new Date().getTime()));
        response.put("redirect", "http://localhost:8080/login");
        return response;
    }

}