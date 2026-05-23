package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    ClientProfile getClientProfile(Authentication authentication);

    //Manager or Admin
    ClientProfile getClient(String email, Authentication authentication);

    List<ClientProfile> getAllClients(Authentication authentication);
}
