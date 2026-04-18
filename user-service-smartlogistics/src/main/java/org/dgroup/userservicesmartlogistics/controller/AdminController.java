package org.dgroup.userservicesmartlogistics.controller;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.admin.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.admin.UserUpdateRoleRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.response.ManagerProfileResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.response.UserResponseDTO;
import org.dgroup.userservicesmartlogistics.mapper.ManagerMapper;
import org.dgroup.userservicesmartlogistics.mapper.UserMapper;
import org.dgroup.userservicesmartlogistics.model.ManagerProfile;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {
    public final UserMapper userMapper;
    public final AdminService adminService;
    public final ManagerMapper managerMapper;


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            Authentication authentication) {
        List<User> users = adminService.getAllUsers(authentication);
        return ResponseEntity.ok(userMapper.toResponseList(users));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(
            @PathVariable UUID id,
            Authentication authentication) {
        User user = adminService.getUser(id, authentication);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PostMapping
    public ResponseEntity<ManagerProfileResponseDTO> createManager(
            @RequestBody CreateManagerRequestDTO dto,
            Authentication authentication) {
        ManagerProfile managerProfile = adminService.createManager(dto, authentication);
        return ResponseEntity.ok(managerMapper.toResponse(managerProfile));
    }

    @PutMapping("block/{id}")
    public ResponseEntity<UserResponseDTO> blockUser(
            @PathVariable UUID id,
            Authentication authentication) {
        User user = adminService.blockUser(id, authentication);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PutMapping("unblock/{id}")
    ResponseEntity<UserResponseDTO> unblockUser(
            @PathVariable UUID id,
            Authentication authentication) {
        User user = adminService.unblockUser(id, authentication);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id,
            Authentication authentication) {
        adminService.deleteUser(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("role/{id}")
    public ResponseEntity<UserResponseDTO> updateUserRole(
            @PathVariable UUID id,
            @RequestBody UserUpdateRoleRequestDTO dto,
            Authentication authentication) {
        User updatedUserRole = adminService.updateUserRole(id, dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(updatedUserRole));
    }
}
