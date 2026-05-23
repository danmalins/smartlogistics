package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserFirstnameAndLastnameRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserPasswordRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserPhoneNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    User updateUserFirstnameAndLastname(String email, UpdateUserFirstnameAndLastnameRequestDTO updateUserFirstnameAndLastnameRequestDTO, Authentication authentication);

    User updateUserPassword(String email, UpdateUserPasswordRequestDTO updatePasswordRequestDTO, Authentication authentication);

    User updateUserPhoneNumber(String email, UpdateUserPhoneNumberRequestDTO updateUserPhoneNumberRequestDTO, Authentication authentication);
}