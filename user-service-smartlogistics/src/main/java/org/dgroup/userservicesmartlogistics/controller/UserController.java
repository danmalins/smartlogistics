package org.dgroup.userservicesmartlogistics.controller;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.UserFirstnameAndLastnameUpdateDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UserUpdatePasswordRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UserUpdatePhoneNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.response.UserResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.UserMapper;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PutMapping("first-and-last-name/{id}")
    public ResponseEntity<UserResponseDTO> updateUserFirstnameAndLastname(
            @PathVariable UUID id,
            @RequestBody UserFirstnameAndLastnameUpdateDTO dto,
            Authentication authentication) {
        User updatedUser = userService.updateUserFirstnameAndLastname(id, dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }

    @PutMapping("password/{id}")
    public ResponseEntity<UserResponseDTO> updateUserPassword(
            @PathVariable UUID id,
            @RequestBody UserUpdatePasswordRequestDTO dto,
            Authentication authentication) {
        User updatedUser = userService.updateUserPassword(id, dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }

    @PutMapping("phone/{id}")
    public ResponseEntity<UserResponseDTO> updateUserPhoneNumber(
            @PathVariable UUID id,
            @RequestBody UserUpdatePhoneNumberRequestDTO dto,
            Authentication authentication) {
        User updatedUser = userService.updateUserPhoneNumber(id, dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }


}
