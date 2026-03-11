package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile,String> {

    Optional<ClientProfile> findByCompanyName(String companyName);

    Optional<ClientProfile> findByCompanyAddress(String companyAddress);

    Optional<ClientProfile> findByTaxNumber(String taxNumber);

    boolean existsByCompanyName(String companyName);

    boolean existsByCompanyAddress(String companyAddress);

    boolean existsByTaxNumber(String taxNumber);
}
