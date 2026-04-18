package org.dgroup.userservicesmartlogistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dgroup.userservicesmartlogistics.dto.auth.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.RegisterClientRequestDTO;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;

public interface AuthService {

    ClientProfile registerClient(RegisterClientRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    void verifyEmail(String token) throws JsonProcessingException;

    void resendVerificationToken(String email);
}
