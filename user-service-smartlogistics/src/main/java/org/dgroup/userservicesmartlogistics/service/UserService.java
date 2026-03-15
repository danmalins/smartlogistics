package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.UserUpdatePasswordRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.UserUpdatePhoneNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User updateUserPassword (UUID id, UserUpdatePasswordRequestDTO updatePasswordRequestDTO, Authentication authentication);

    User updateUserPhoneNumber (UUID id, UserUpdatePhoneNumberRequestDTO userUpdatePhoneNumberRequestDTO, Authentication authentication);
}