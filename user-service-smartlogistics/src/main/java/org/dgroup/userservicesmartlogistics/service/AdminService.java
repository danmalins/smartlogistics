package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.admin.CreateAdminRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.manager.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UserUpdateRoleRequestDTO;
import org.dgroup.userservicesmartlogistics.model.ManagerProfile;
import org.dgroup.userservicesmartlogistics.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AdminService {

    List<User> getAllUsers(Authentication authentication);

    User getUser(String email, Authentication authentication);

    List<ManagerProfile> getAllManagers(Authentication authentication);

    ManagerProfile getManager(String email, Authentication authentication);

    User createAdmin(CreateAdminRequestDTO dto, Authentication authentication);

    ManagerProfile createManager(CreateManagerRequestDTO dto, Authentication authentication);

    User blockUser(String email, Authentication authentication);

    User unblockUser(String email, Authentication authentication);

    void deleteUser(String email, Authentication authentication);

    User updateUserRole(String email, UserUpdateRoleRequestDTO dto, Authentication authentication);
}
