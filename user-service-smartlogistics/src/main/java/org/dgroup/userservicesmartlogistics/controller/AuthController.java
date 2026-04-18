package org.dgroup.userservicesmartlogistics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.auth.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.RegisterClientRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.response.ClientProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.ClientMapper;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientMapper clientMapper;
    private final AuthService authService;

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

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {
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