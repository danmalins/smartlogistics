package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.RegisterClientRequestDTO;

public interface AuthService {
    void registerClient(RegisterClientRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    void verifyEmail(String token);

    void resendVerificationToken(String email);
}
