package org.dgroup.userservicesmartlogistics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.response.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.request.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.auth.RegisterClientRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.response.ClientProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.ClientMapper;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.security.JwtService;
import org.dgroup.userservicesmartlogistics.service.AuthService;
import org.dgroup.userservicesmartlogistics.service.TokenBlacklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientMapper clientMapper;
    private final AuthService authService;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/signup")
    public ResponseEntity<ClientProfileResponseDTO> registerClient(
           @Valid @RequestBody RegisterClientRequestDTO request) {
        ClientProfile clientProfile = authService.registerClient(request);
        return ResponseEntity.ok(clientMapper.toResponse(clientProfile));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody LoginRequestDTO request) {
        AuthResponseDTO authResponseDTO = authService.login(request);
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            long ttl = jwtService.getRemainingValidity(token);
            if (ttl < 0) ttl = 0;
            tokenBlacklistService.blacklistToken(token, ttl);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) throws JsonProcessingException {
        authService.verifyEmail(token);
        return ResponseEntity.ok("Email successfully verified!");
    }

    @GetMapping("/resend-token")
    public ResponseEntity<String> resendVerificationToken(
            String email) {
        authService.resendVerificationToken(email);
        return ResponseEntity.ok("Verification token has successfully resented");
    }
}