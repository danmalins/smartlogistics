package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminProfileRepository extends JpaRepository<AdminProfile, String> {
    List<User> getAllUsers();

    void blockUser(String email);

    void unblockUser(String email);

    void changeUserRole(String email, UserRole role);

    void deleteUser(String email);

    List<DriverProfile> getAllDrivers();

    List<ClientProfile> getAllClients();

    List<ManagerProfile> getAllManagers();
}
