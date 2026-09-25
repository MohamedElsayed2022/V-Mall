package com.api.config.auth;


import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) throws MessagingException {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/activate-account")
    public ResponseEntity<String> activateAccount(@RequestParam String token){
        authenticationService.activateAccount(token);
        return ResponseEntity.ok("Account activated");
    }
}
