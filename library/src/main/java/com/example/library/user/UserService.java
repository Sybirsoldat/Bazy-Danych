package com.example.library.user;

import com.example.library.email.EmailSender;
import com.example.library.registration.EmailValidator;
import com.example.library.registration.PasswordValidator;
import com.example.library.registration.token.ConfirmationToken;
import com.example.library.registration.token.ConfirmationTokenService;
import com.example.library.services.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG_USERNAME = "Nie znaleziono użytkownika o nicku %s";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final PasswordValidator passwordValidator;
    private final EmailValidator emailValidator;

    @Autowired
    private ActivityLogService activityLogService;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, ConfirmationTokenService confirmationTokenService, EmailSender emailSender, PasswordValidator passwordValidator, EmailValidator emailValidator) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
        this.passwordValidator = passwordValidator;
        this.emailValidator = emailValidator;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void enableUser(String email) {
        System.out.println("Aktywacja użytkownika: " + email);
        userRepository.enableUser(email);
        userRepository.flush();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public String createUser(User user) {
        // Check for existing users by username or email
        Optional<User> existingUserOpt = userRepository.findByLoginOrEmail(user.getLogin(), user.getEmail());
        String token = UUID.randomUUID().toString();

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (!existingUser.getEnabled()) {
                if (!existingUser.getEmail().equals(user.getEmail())) {
                    throw new IllegalStateException("Username is already taken.");
                } else {
                    ConfirmationToken confirmationToken = new ConfirmationToken(
                            token,
                            new Timestamp(new Date().getTime()),
                            // 15 minutes expiration
                            new Timestamp(new Date().getTime() + 1000 * 60 * 15),
                            existingUser
                    );
                    confirmationTokenService.saveConfirmationToken(confirmationToken);

                    // Send email with the new token
                    String link = "http://localhost:8080/confirm?token=" + token;
                    emailSender.send(
                            existingUser.getEmail(),
                            buildConfirmationEmail(existingUser.getFirstname(), link),
                            "Account Activation | Library System"
                    );
                    throw new IllegalStateException("Account not activated. Check your email for activation.");
                }
            } else {
                throw new IllegalStateException("Username or email is already taken.");
            }
        }

        // Validate email and password
        boolean isValidEmail = emailValidator.test(user.getEmail());
        boolean isValidPassword = passwordValidator.test(user.getPassword());

        if (!isValidEmail) {
            throw new IllegalStateException("Invalid email format.");
        }

        if (!isValidPassword) {
            throw new IllegalStateException("Password does not meet security requirements.");
        }

        // Encode the password before saving the user
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Save confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                new Timestamp(new Date().getTime()),
                // 15 minutes expiration
                new Timestamp(new Date().getTime() + 1000 * 60 * 15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    private String buildConfirmationEmail(String firstName, String link) {
        return "Witaj " + firstName + ",<br/>" +
                "Kliknij <a href=\"" + link + "\">tutaj</a>, aby aktywować konto";
    }


    public User updateUser(Long id, User userDetails) {
        User existingUser = getUserById(id);

        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(existingUser.getEmail())) {
            validateUniqueEmail(userDetails.getEmail());
            existingUser.setEmail(userDetails.getEmail());
        }
        if (userDetails.getMobilePhone() != null && !userDetails.getMobilePhone().equals(existingUser.getMobilePhone())) {
            validateUniqueMobilePhone(userDetails.getMobilePhone());
            existingUser.setMobilePhone(userDetails.getMobilePhone());
        }
        existingUser.setFirstname(userDetails.getFirstname());
        existingUser.setSurname(userDetails.getSurname());
        existingUser.setAddress(userDetails.getAddress());


        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    void validateUniqueFields(User user) {
        validateUniqueEmail(user.getEmail());
        validateUniqueMobilePhone(user.getMobilePhone());
    }

    void validateUniqueEmail(String email) {
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new RuntimeException("Email already exists");
        });
    }

    void validateUniqueMobilePhone(String mobilePhone) {
        userRepository.findByMobilePhone(mobilePhone).ifPresent(u -> {
            throw new RuntimeException("Mobile phone already exists");
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
