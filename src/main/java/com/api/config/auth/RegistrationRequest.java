package com.api.config.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotBlank(message = "firstName is Required")
    @NotEmpty(message = "firstName is Required")
    private String firstName;
    @NotBlank(message = "lastName is Required")
    @NotEmpty(message = "lastName is Required")
    private String lastName;
    @NotBlank(message = "Email is Required")
    @NotEmpty(message = "Email is Required")
    @Email(message = "Email is Not Formated")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password is Required")
    @NotEmpty(message = "Password is Required")
    @Size(min = 8 , message = "Password should be between 8 characters long minimum")
    private String password;
}
