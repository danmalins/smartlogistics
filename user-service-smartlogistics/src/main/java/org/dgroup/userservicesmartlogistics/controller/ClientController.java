package org.dgroup.userservicesmartlogistics.controller;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.response.ClientProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.ClientMapper;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientMapper clientMapper;
    private final ClientService clientService;

    @GetMapping("/profile")
    public ResponseEntity<ClientProfileResponseDTO> getClientProfile(
            Authentication authentication) {
        ClientProfile clientInfo = clientService.getClientProfile(authentication);
        return ResponseEntity.ok(clientMapper.toResponse(clientInfo));
    }
}
