package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface ClientService {

    ClientProfile getClientrofile(Authentication authentication);

    ClientProfile updateClientProfile(
            ClientUpdateRequestDTO requestDTO,
            Authentication authentication
    );

    void deleteClientProfile(Authentication authentication);
}
