package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRole(UserRole role);

    Optional<User> findByFirstName(String firstname);

    Optional<User> findByLastName(String lastname);

    Optional<User> findByFirstNameAndLastName(String firstname, String lastname);

    Optional<User> findByPhone(String phoneNumber);

    Optional<User> findByEnabled(boolean enabled);

    boolean existsByEmail(String email);
}
