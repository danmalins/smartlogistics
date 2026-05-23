package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserFirstnameAndLastnameRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserPasswordRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UpdateUserPhoneNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.user.UserUpdateRoleRequestDTO;
import org.dgroup.userservicesmartlogistics.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    User updateUserFirstnameAndLastname(String email, UpdateUserFirstnameAndLastnameRequestDTO updateUserFirstnameAndLastnameRequestDTO, Authentication authentication);

    User updateUserPassword(String email, UpdateUserPasswordRequestDTO updatePasswordRequestDTO, Authentication authentication);

    User updateUserPhoneNumber(String email, UpdateUserPhoneNumberRequestDTO updateUserPhoneNumberRequestDTO, Authentication authentication);

    User getUser(String email, Authentication authentication);

    List<User> getAllUsers(Authentication authentication);

    User blockUser(String email, Authentication authentication);

    User unblockUser(String email, Authentication authentication);

    void deleteUser(String email, Authentication authentication);

    User updateUserRole(String email, UserUpdateRoleRequestDTO dto, Authentication authentication);
}
