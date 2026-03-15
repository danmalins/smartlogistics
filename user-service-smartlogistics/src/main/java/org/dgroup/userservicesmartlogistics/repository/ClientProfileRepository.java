package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile, UUID> {

    Optional<ClientProfile> findByUser(User user);

    Optional<ClientProfile> findByUserEmail(String email);

    Optional<ClientProfile> findByTaxNumber(String taxNumber);

    boolean existsByTaxNumber(String taxNumber);
}
