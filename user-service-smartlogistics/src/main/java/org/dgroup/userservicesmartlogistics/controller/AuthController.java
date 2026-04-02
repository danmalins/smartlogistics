package org.dgroup.userservicesmartlogistics.controller;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.auth.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.RegisterClientRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.response.ClientProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.ClientMapper;
import org.dgroup.userservicesmartlogistics.mapper.UserMapper;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.service.AuthService;
import org.dgroup.userservicesmartlogistics.service.ClientService;
import org.dgroup.userservicesmartlogistics.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientMapper clientMapper;
    private final ClientService clientService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ClientProfileResponseDTO> registerClient(
            @RequestBody RegisterClientRequestDTO request) {
        ClientProfile clientProfile = authService.registerClient(request);
        return ResponseEntity.ok(clientMapper.toResponse(clientProfile));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody LoginRequestDTO request) {
        AuthResponseDTO authResponseDTO = authService.login(request);
        return ResponseEntity.ok(authResponseDTO);
    }

//    void verifyEmail(String token);
//
//    void resendVerificationToken(String email);
}
