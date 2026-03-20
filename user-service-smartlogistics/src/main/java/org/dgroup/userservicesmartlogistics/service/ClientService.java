package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.springframework.security.core.Authentication;

public interface ClientService {

    ClientProfile getClientProfile(Authentication authentication);

}
