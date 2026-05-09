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

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PutMapping("first-and-last-name/{email}")
    public ResponseEntity<UserResponseDTO> updateUserFirstnameAndLastname(
            @PathVariable String email,
            @RequestBody UserFirstnameAndLastnameUpdateDTO dto,
            Authentication authentication) {
        User updatedUser = userService.updateUserFirstnameAndLastname(email, dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }

    @PutMapping("password/{email}")
    public ResponseEntity<UserResponseDTO> updateUserPassword(
            @PathVariable String email,
            @RequestBody UserUpdatePasswordRequestDTO dto,
            Authentication authentication) {
        User updatedUser = userService.updateUserPassword(email, dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }

    @PutMapping("phone/{email}")
    public ResponseEntity<UserResponseDTO> updateUserPhoneNumber(
            @PathVariable String email,
            @RequestBody UserUpdatePhoneNumberRequestDTO dto,
            Authentication authentication) {
        User updatedUser = userService.updateUserPhoneNumber(email, dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }


}
