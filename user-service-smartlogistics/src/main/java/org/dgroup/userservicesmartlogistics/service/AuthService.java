package org.dgroup.userservicesmartlogistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dgroup.userservicesmartlogistics.dto.response.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.request.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.auth.RegisterClientRequestDTO;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;

public interface AuthService {

    ClientProfile registerClient(RegisterClientRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    void verifyEmail(String token) throws JsonProcessingException;

    void resendVerificationToken(String email);
}
