package org.dgroup.userservicesmartlogistics.service;

import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.UserNotFoundException;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService  {

    private final UserRepository userRepository;

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
        if (userRepository.findById(userId).isEmpty())
            throw new UserNotFoundException("User with id: '" + userId + "' not found");
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void blockUser(UUID userId, Authentication authentication) {
        if (!isAdmin(authentication))
            throw new CustomAccessDeniedException("Only admins can block user by id");
        if (userRepository.findById(userId).isEmpty())
            throw new UserNotFoundException("User with id: '" + userId + "' not found");
        User user = userRepository.findById(userId).orElse(null);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(UUID userId, Authentication authentication) {

    }

    @Override
    public void deleteUser(UUID userId, Authentication authentication) {

    }

    @Override
    public void changeUserRole(UUID userId, UserRole role, Authentication authentication) {

    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}
