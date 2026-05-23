package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.driver.CreateDriverRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.driver.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.driver.UpdateDriverTruckInfoRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.manager.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.ClientNotFoundException;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.DriverNotFoundException;
import org.dgroup.userservicesmartlogistics.exception.UserNotFoundException;
import org.dgroup.userservicesmartlogistics.model.*;
import org.dgroup.userservicesmartlogistics.repository.ClientProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.DriverProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.ManagerProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerProfileRepository managerProfileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
