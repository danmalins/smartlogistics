package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserFirstnameAndLastnameRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserPasswordRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserPhoneNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UserUpdateRoleRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.UserNotFoundException;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.dgroup.userservicesmartlogistics.repository.VerificationTokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

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
    public User updateUserFirstnameAndLastname(String email, UpdateUserFirstnameAndLastnameRequestDTO dto, Authentication authentication) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with email '" + email + "' not found."));

        if (!isAdmin(authentication) && !user.getEmail().equals(authentication.getName()))
            throw new CustomAccessDeniedException("You can only update your own profile.");

        user.setFirstName(dto.getNewFirstname());
        user.setLastName(dto.getNewLastname());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User updateUserPassword(String email, UpdateUserPasswordRequestDTO dto, Authentication authentication) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with email '" + email + "' not found."));

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
    public User updateUserPhoneNumber(String email, UpdateUserPhoneNumberRequestDTO dto, Authentication authentication) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                "User with email '" + email + "' not found"));

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
