package org.dgroup.userservicesmartlogistics.controller;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.admin.CreateAdminRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.manager.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UserUpdateRoleRequestDTO;
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

    @GetMapping("{email}")
    public ResponseEntity<UserResponseDTO> getUser(
            @PathVariable String email,
            Authentication authentication) {
        User user = adminService.getUser(email, authentication);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PostMapping("create-manager")
    public ResponseEntity<ManagerProfileResponseDTO> createManager(
            @RequestBody CreateManagerRequestDTO dto,
            Authentication authentication) {
        ManagerProfile managerProfile = adminService.createManager(dto, authentication);
        return ResponseEntity.ok(managerMapper.toResponse(managerProfile));
    }

    @PostMapping("create-admin")
    public ResponseEntity<UserResponseDTO> createAdmin(
            @RequestBody CreateAdminRequestDTO dto,
            Authentication authentication) {
        User admin = adminService.createAdmin(dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(admin));
    }

    @PutMapping("block/{email}")
    public ResponseEntity<UserResponseDTO> blockUser(
            @PathVariable String email,
            Authentication authentication) {
        User user = adminService.blockUser(email, authentication);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PutMapping("unblock/{email}")
    ResponseEntity<UserResponseDTO> unblockUser(
            @PathVariable String email,
            Authentication authentication) {
        User user = adminService.unblockUser(email, authentication);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @DeleteMapping("{email}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String email,
            Authentication authentication) {
        adminService.deleteUser(email, authentication);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("role/{email}") //unnecessary for now
    public ResponseEntity<UserResponseDTO> updateUserRole(
            @PathVariable String email,
            @RequestBody UserUpdateRoleRequestDTO dto,
            Authentication authentication) {
        User updatedUserRole = adminService.updateUserRole(email, dto, authentication);
        return ResponseEntity.ok(userMapper.toResponse(updatedUserRole));
    }
}
