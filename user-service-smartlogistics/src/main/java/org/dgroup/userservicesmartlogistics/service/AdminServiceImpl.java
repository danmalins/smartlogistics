package org.dgroup.userservicesmartlogistics.service;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.admin.CreateAdminRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.manager.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UserUpdateRoleRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.UserNotFoundException;
import org.dgroup.userservicesmartlogistics.model.ManagerProfile;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;
import org.dgroup.userservicesmartlogistics.repository.ManagerProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.dgroup.userservicesmartlogistics.repository.VerificationTokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManagerProfileRepository managerProfileRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public ManagerProfile createManager(CreateManagerRequestDTO dto, Authentication authentication) {

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
                .verified(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.saveAndFlush(user);

        ManagerProfile manager = ManagerProfile.builder()
                .user(savedUser)
                .department(dto.getDepartment())
                .employeeNumber(dto.getEmployeeNumber())
                .createdAt(LocalDateTime.now())
                .build();

        return managerProfileRepository.save(manager);
    }

    @Override
    public List<User> getAllUsers(Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can access all users");
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can access user by email");
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + email + "' not found."));
    }

    @Override
    public List<ManagerProfile> getAllManagers(Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can access all managers");
        return managerProfileRepository.findAll();
    }

    @Override
    public ManagerProfile getManager(String email, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can access manager by email");
        return managerProfileRepository.findByUserEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + email + "' not found."));
    }

    @Override
    public User createAdmin(CreateAdminRequestDTO dto, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admin can create another admin");

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRole.ROLE_ADMIN)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .enabled(true) // менеджеров можно сразу активировать
                .verified(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    @Override
    public User blockUser(String email, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can block user by email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with email '" + email + "' not found."));
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public User unblockUser(String email, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can unblock user by email");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + email + "' not found."));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String email, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can delete user");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + email + "' not found."));
        verificationTokenRepository.deleteByUser(user);
        userRepository.delete(user);
    }

    @Override
    public User updateUserRole(String email, UserUpdateRoleRequestDTO dto, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can change user role");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + email + "' not found."));
        user.setRole(dto.getRole());
        return userRepository.save(user);
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
