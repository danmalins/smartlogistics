package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.auth.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.RegisterClientRequestDTO;

public interface AuthService {

    void registerClient(RegisterClientRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    void verifyEmail(String token);

    void resendVerificationToken(String email);
}
