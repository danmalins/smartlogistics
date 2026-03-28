package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.auth.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.RegisterClientRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.*;
import org.dgroup.userservicesmartlogistics.model.*;
import org.dgroup.userservicesmartlogistics.repository.ClientProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.dgroup.userservicesmartlogistics.repository.VerificationTokenRepository;
import org.dgroup.userservicesmartlogistics.security.JwtService;
import org.dgroup.userservicesmartlogistics.security.MyUserDetails;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ClientProfileRepository clientProfileRepository;
    private final VerificationTokenService verificationTokenService;
    private final VerificationTokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EmailValidator emailValidator;

    @Override
    public void registerClient(RegisterClientRequestDTO request) {

        if (!emailValidator.isValid(request.getEmail(), null)) {
            throw new RuntimeException("Invalid email format");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_CLIENT)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .enabled(true)
                .verified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ClientProfile clientProfile = ClientProfile.builder()
                .user(user)
                .companyName(request.getCompanyName())
                .companyAddress(request.getCompanyAddress())
                .taxNumber(request.getTaxNumber())
                .createdAt(LocalDateTime.now())
                .build();

        user.setClientProfile(clientProfile);

        User savedUser = userRepository.save(user);

        VerificationToken vt = verificationTokenService.createTokenForUser(savedUser);

        // Відправляємо верифікаційний лист
//        VerificationEmailEvent verificationEvent = VerificationEmailEvent.builder()
//                .email(savedUser.getEmail())
//                .username(savedUser.getUsername())
//                .token(vt.getToken())
////                .verificationUrl("http://localhost:8080/api/users/verify?token=" + vt.getToken())
//                .build();
//        userEventProducer.publishVerificationEmail(verificationEvent);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            User user = myUserDetails.getUser();

            return AuthResponseDTO.builder()
                    .token(jwtService.generateToken(
                            user.getEmail(),
                            user.getRole().name()))
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();

        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Invalid email or password");

        } catch (DisabledException e) {
            throw new UserNotVerifiedException("User is not verified or blocked");
        }
    }

    @Override
    public void verifyEmail(String token) {
        VerificationToken vt = verificationTokenService.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid verification token"));

        if (vt.getExpiryDate().isBefore(LocalDateTime.now())) {
            verificationTokenService.deleteToken(token);
            throw new TokenExpiredException("Verification token expired");
        }

        User user = vt.getUser();
        user.setVerified(true);
        userRepository.save(user);
        verificationTokenService.deleteToken(token);

        // Тільки тут відправляємо Welcome event
//        UserRegisteredEvent event = UserRegisteredEvent.builder()
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .build();
//        userEventProducer.publishUserRegistered(event);
    }

    @Override
    public void resendVerificationToken(String email) {

        // 1. Валидация email
        if (!emailValidator.isValid(email, null)) {
            throw new RuntimeException("Invalid email format");
        }

        // 2. Найти пользователя
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "User with email '" + email + "' not found"
                ));

        // 3. Проверка: пользователь не заблокирован
        if (!user.isEnabled()) {
            throw new RuntimeException("User is blocked");
        }

        // 4. Проверка: уже подтверждён
        if (user.isVerified()) {
            throw new RuntimeException("User already verified");
        }

        // 5. (опционально) проверка роли
        if (user.getRole() != UserRole.ROLE_CLIENT) {
            throw new RuntimeException("Only clients can request verification");
        }

        // 6. Удалить старые токены
        tokenRepository.deleteByUser(user);

        // 7. Создать новый токен
        VerificationToken newToken = verificationTokenService.createTokenForUser(user);

        // 8. Отправить email Kafka
        //emailService.sendVerificationEmail(user.getEmail(), newToken.getToken());
    }
}
