package com.api.config.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotEmpty(message = "Please Enter Valid Email")
    @NotBlank(message = "Please Enter Valid Email")
    private String email;
    @NotEmpty(message = "Please Enter Valid Password")
    @NotBlank(message = "Please Enter Valid Password")
    private String password;
}
