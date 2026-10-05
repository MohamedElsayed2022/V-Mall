package com.api.config.auth;

import com.api.config.auth.email.EmailService;
import com.api.config.auth.email.EmailTemplateName;
import com.api.config.security.JwtService;
import com.api.model.RefreshToken;
import com.api.model.Token;
import com.api.model.User;
import com.api.repository.RefreshTokenRepository;
import com.api.repository.RoleRepository;
import com.api.repository.TokenRepository;
import com.api.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


    @Transactional
    public void register(RegistrationRequest request) throws MessagingException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        var userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role User was not initialized"));
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(0)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
        System.out.println("REGISTER HIT");

    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = generateRefreshToken(user);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Transactional
    public void activateAccount(String token) {

        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        User user = savedToken.getUser();

        user.setActive(1);

        userRepository.save(user);

        tokenRepository.delete(savedToken);
    }
    private void sendValidationEmail(User user) throws MessagingException {

        var newToken = generateAndSaveActivation(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl + "?token=" + newToken,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivation(User user) {
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        String chars = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();

        for (int j = 0; j < 6; j++) {
            int randomIndex = random.nextInt(chars.length());
            codeBuilder.append(chars.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    private String generateRefreshToken(User user) {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);

        String refreshToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .expiredAt(LocalDateTime.now().plusDays(7))
                .user(user)
                .build();

        refreshTokenRepository.save(token);
        return refreshToken;

    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request){
        RefreshToken token = refreshTokenRepository.findByToken(request.refreshToken()).orElseThrow(
                () -> new RuntimeException("Invalid token"));


        if (token.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        User user = token.getUser();

        String jwtToken = jwtService.generateToken(user);


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(token.getToken())
                .build();
    }
}
