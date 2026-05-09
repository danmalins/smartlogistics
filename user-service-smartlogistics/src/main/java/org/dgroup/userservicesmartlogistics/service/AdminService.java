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

    User getUser(String email, Authentication authentication);

    ManagerProfile createManager(CreateManagerRequestDTO dto, Authentication authentication);

    User blockUser(String email, Authentication authentication);

    User unblockUser(String email, Authentication authentication);

    void deleteUser(String email, Authentication authentication);

    User updateUserRole(String email, UserUpdateRoleRequestDTO dto, Authentication authentication);
}
