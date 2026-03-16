package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.UpdateClientProfileRequestDTO;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.springframework.security.core.Authentication;

public interface ClientService {

    ClientProfile getClientProfile(Authentication authentication);

    ClientProfile updateClientProfile(
            UpdateClientProfileRequestDTO requestDTO,
            Authentication authentication
    );

    void deleteClientProfile(Authentication authentication);
}
