package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.UserFirstnameAndLastnameUpdateDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UserUpdatePasswordRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UserUpdatePhoneNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.UserNotFoundException;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User updateUserFirstnameAndLastname(UUID id, UserFirstnameAndLastnameUpdateDTO dto, Authentication authentication) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + id + "' not found."));

        if (!isAdmin(authentication) && !user.getEmail().equals(authentication.getName()))
            throw new CustomAccessDeniedException("You can only update your own profile.");

        user.setFirstName(dto.getNewFirstname());
        user.setLastName(dto.getNewLastname());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User updateUserPassword(UUID id, UserUpdatePasswordRequestDTO dto, Authentication authentication) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + id + "' not found."));

        if (!isAdmin(authentication) && !user.getEmail().equals(authentication.getName()))
            throw new CustomAccessDeniedException("You can only update your own profile.");

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User updateUserPhoneNumber(UUID id, UserUpdatePhoneNumberRequestDTO dto, Authentication authentication) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                "User with id '" + id + "' not found"));

        if (!isAdmin(authentication) && !user.getEmail().equals(authentication.getName()))
            throw new CustomAccessDeniedException("You can only update your own profile.");

        user.setPhone(dto.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
