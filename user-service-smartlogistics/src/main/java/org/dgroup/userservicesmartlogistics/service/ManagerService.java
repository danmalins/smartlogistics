package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.manager.CreateManagerRequestDTO;
import org.dgroup.userservicesmartlogistics.model.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ManagerService {

    List<ManagerProfile> getAllManagers(Authentication authentication);

    ManagerProfile getManager(String email, Authentication authentication);

    ManagerProfile createManager(CreateManagerRequestDTO dto, Authentication authentication);
}
