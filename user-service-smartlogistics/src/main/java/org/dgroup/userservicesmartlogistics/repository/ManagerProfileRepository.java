package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.ManagerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManagerProfileRepository extends JpaRepository<ManagerProfile, UUID> {

    Optional<ManagerProfile> findByDepartment (String department);

    Optional<ManagerProfile> findByEmployeeNumber(String employeeNumber);

    boolean existsByEmployeeNumber(String employeeNumber);

}
