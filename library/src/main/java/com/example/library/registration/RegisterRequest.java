package com.example.library.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterRequest {

    @NotBlank(message = "Username cannot be blank")
    private final String username;

    @NotBlank(message = "Password cannot be blank")
    private final String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private final String email;

    @NotBlank(message = "First name cannot be blank")
    private final String firstName;

    @NotBlank(message = "Surname cannot be blank")
    private final String surname;

    private final String patronymic; // Optional, can be null or blank

    @NotNull(message = "Gender ID is required")
    private final Long genderId;

    @NotBlank(message = "Address cannot be blank")
    private final String address;

    @NotBlank(message = "Mobile phone cannot be blank")
    private final String mobilePhone;

    @NotNull(message = "Role ID is required")
    private final Long roleId;
}
