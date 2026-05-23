package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.admin.CreateAdminRequestDTO;
import org.dgroup.userservicesmartlogistics.model.User;
import org.springframework.security.core.Authentication;

public interface AdminService {

    User createAdmin(CreateAdminRequestDTO dto, Authentication authentication);
}
