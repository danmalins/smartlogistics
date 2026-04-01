package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.admin.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.admin.UserUpdateRoleRequestDTO;
import org.dgroup.userservicesmartlogistics.model.ManagerProfile;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<User> getAllUsers(Authentication authentication);

    User getUser(UUID userId, Authentication authentication);

    ManagerProfile createManager(CreateManagerRequestDTO dto, Authentication authentication);

    User blockUser(UUID userId, Authentication authentication);

    User unblockUser(UUID userId, Authentication authentication);

    void deleteUser(UUID userId, Authentication authentication);

    User updateUserRole(UUID userId, UserUpdateRoleRequestDTO dto, Authentication authentication);
}
