package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.admin.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<User> getAllUsers(Authentication authentication);

    User getUser(UUID userId, Authentication authentication);

    void blockUser(UUID userId, Authentication authentication);

    void unblockUser(UUID userId, Authentication authentication);

    void deleteUser(UUID userId, Authentication authentication);

    void changeUserRole(UUID userId, UserRole role,  Authentication authentication);
}
