package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.dgroup.commonevents.VerificationEmailEvent;
import org.dgroup.userservicesmartlogistics.dto.auth.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.RegisterClientRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.*;
import org.dgroup.userservicesmartlogistics.kafka.UserEventProducer;
import org.dgroup.userservicesmartlogistics.model.*;
import org.dgroup.userservicesmartlogistics.repository.ClientProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.dgroup.userservicesmartlogistics.repository.VerificationTokenRepository;
import org.dgroup.userservicesmartlogistics.security.JwtService;
import org.dgroup.userservicesmartlogistics.security.MyUserDetails;
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

    private final UserEventProducer userEventProducer;
    private final VerificationRateLimitService verificationRateLimitService;

    @Override
    public ClientProfile registerClient(RegisterClientRequestDTO request) {

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
        sendVerificationEmail(savedUser, vt.getToken());

        return savedUser.getClientProfile();
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

            if (!user.isEnabled())
                throw new RuntimeException("User is blocked");

            if (!user.isVerified())
                throw new UserNotVerifiedException("Email not verified");


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

        if (!user.isEnabled())
            throw new RuntimeException("User is blocked");

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

        // 🔥 RATE LIMIT (САМОЕ ПЕРВОЕ)
        verificationRateLimitService.checkRateLimit(email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "User with email '" + email + "' not found"
                ));

        if (!user.isEnabled()) {
            throw new RuntimeException("User is blocked");
        }

        if (user.isVerified()) {
            throw new RuntimeException("User already verified");
        }

        if (!user.getRole().equals(UserRole.ROLE_CLIENT)) {
            throw new RuntimeException("Access denied");
        }

        tokenRepository.deleteByUser(user);

        VerificationToken newToken = verificationTokenService.createTokenForUser(user);

        sendVerificationEmail(user, newToken.getToken());
    }

    private void sendVerificationEmail(User user, String token) {
        if (!user.isEnabled())
            throw new RuntimeException("User with email '" + user.getEmail() + "' is blocked");
        VerificationEmailEvent event = VerificationEmailEvent.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .token(token)
                .build();

        userEventProducer.publishVerificationEmail(event);
    }
}
