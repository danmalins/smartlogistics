package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<User> getAllUsers();

    User getUser(UUID userId);

    void blockUser(UUID userId);

    void unblockUser(UUID userId);

    void deleteUser(UUID userId);

    void changeUserRole(UUID userId, UserRole role);
}
