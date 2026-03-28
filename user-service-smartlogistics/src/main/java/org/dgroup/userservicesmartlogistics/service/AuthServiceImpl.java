package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.auth.AuthResponseDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.LoginRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.auth.RegisterClientRequestDTO;

public class AuthServiceImpl implements  AuthService {



    @Override
    public void registerClient(RegisterClientRequestDTO request) {

    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        return null;
    }

    @Override
    public void verifyEmail(String token) {

    }

    @Override
    public void resendVerificationToken(String email) {

    }
}
