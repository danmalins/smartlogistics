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

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
