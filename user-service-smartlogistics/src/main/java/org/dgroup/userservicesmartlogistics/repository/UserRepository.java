package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRole(UserRole role);

    Optional<User> findByFirstNameAndLastName(String firstname, String lastname);

    Optional<User> findByPhone(String phoneNumber);

    boolean existsByEmail(String email);
}
