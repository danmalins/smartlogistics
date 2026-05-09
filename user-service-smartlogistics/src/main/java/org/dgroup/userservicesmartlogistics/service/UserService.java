package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.UserFirstnameAndLastnameUpdateDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UserUpdatePasswordRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UserUpdatePhoneNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.model.User;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UserService {

    User updateUserFirstnameAndLastname(String email, UserFirstnameAndLastnameUpdateDTO userFirstnameAndLastnameUpdateDTO, Authentication authentication);

    User updateUserPassword(String email, UserUpdatePasswordRequestDTO updatePasswordRequestDTO, Authentication authentication);

    User updateUserPhoneNumber(String email, UserUpdatePhoneNumberRequestDTO userUpdatePhoneNumberRequestDTO, Authentication authentication);
}