package org.dgroup.userservicesmartlogistics.service;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.admin.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.UserNotFoundException;
import org.dgroup.userservicesmartlogistics.model.ManagerProfile;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;
import org.dgroup.userservicesmartlogistics.repository.ManagerProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManagerProfileRepository managerProfileRepository;

    @Override
    public void createManager(CreateManagerRequestDTO dto, Authentication authentication) {

        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admin can create manager");

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRole.ROLE_MANAGER)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .enabled(true) // менеджеров можно сразу активировать
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        ManagerProfile manager = ManagerProfile.builder()
                .user(user)
                .department(dto.getDepartment())
                .employeeNumber(dto.getEmployeeNumber())
                .createdAt(LocalDateTime.now())
                .build();

        managerProfileRepository.save(manager);
    }

    @Override
    public List<User> getAllUsers(Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can access all users");
        return userRepository.findAll();
    }

    @Override
    public User getUser(UUID userId, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can access user by id");
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + userId + "' not found."));
    }

    @Override
    public void blockUser(UUID userId, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can block user by id");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + userId + "' not found."));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(UUID userId, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can unblock user by id");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + userId + "' not found."));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can delete user");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + userId + "' not found."));
        userRepository.delete(user);
    }

    @Override
    public void changeUserRole(UUID userId, UserRole role, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can change user role");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + userId + "' not found."));
        user.setRole(role);
        userRepository.save(user);
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
