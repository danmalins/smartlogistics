package org.dgroup.userservicesmartlogistics.service;

public interface AuthService {
    void registerClient(RegisterClientRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    void verifyEmail(String token);

    void resendVerificationToken(String email);
}
